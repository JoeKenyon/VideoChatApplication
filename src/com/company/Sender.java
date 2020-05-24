package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Sender implements Runnable
{
    static DatagramSocket sending_socket;
    static int SEQ_NUMBER     = 0;
    static byte[][] sent_data;



    void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        boolean running        = true;
        int PORT               = 55555;
        AudioRecorder recorder = null;
        InetAddress clientIP   = null;

        sent_data              = new byte[Utils.SCREEN_HEIGHT][Utils.SCREEN_WIDTH*3];

        try
        {
            recorder = new AudioRecorder();
            sending_socket = new DatagramSocket();
            clientIP = InetAddress.getByName("localhost");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }


        while (running)
        {
            try
            {

                // get video data
                byte[] video_data = Utils.getVideoData();

                // send one row at a time
                for(int i = 0; i < 500; i++)
                {
                    // payload buffer
                    byte[] payload = new byte[750*3];

                    // ensure data is there
                    assert video_data != null;

                    // copy data to payload buffer
                    System.arraycopy(video_data, (payload.length * i), payload, 0, payload.length);

                    // check with previously sent frame
                    // if there's no difference, send a 0
                    // compression should remove all of these

                    byte[] payload_original = payload.clone();

                    for(int j = 0; j < payload.length; j++)
                    {
                        if(payload[j] == sent_data[i][j])
                        {
                            payload[j] = 0;
                        }
                    }

                    sent_data[i] = payload_original;

                    // compress data
                    payload = GoldFoilCompression.compress(payload);

                    RTPVideoPacket rtp = new RTPVideoPacket(SEQ_NUMBER++,0, i, payload);

                    byte[] packetData = rtp.toBytes();

                    DatagramPacket packet = new DatagramPacket(packetData, packetData.length, clientIP, PORT);

                    sending_socket.send(packet);

                    // send voice packet
                    RTPAudioPacket rtpA = new RTPAudioPacket(SEQ_NUMBER++, GoldFoilCompression.compress(recorder.getBlock()));

                    packetData = rtpA.toBytes();

                    packet = new DatagramPacket(packetData, packetData.length, clientIP, PORT);

                    sending_socket.send(packet);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        //Close the socket
        sending_socket.close();
    }
}
