package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Sender implements Runnable
{
    static DatagramSocket sending_socket;
    static byte[] PREV_FRAME;
    static int SEQ_NUMBER     = 0;
    static int SIZE_OF_CHUNKS = 29;
    static int HEADER_SIZE    = Integer.BYTES*4;

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

                byte[] video_data = Utils.getVideoData();

                for(int i = 0; i < 500; i++)
                {
                    byte[] payload = new byte[750*3];

                    assert video_data != null;

                    System.arraycopy(video_data, (payload.length * i), payload, 0, payload.length);

                    payload = Utils.compress(payload);

                    RTPVideoPacket rtp = new RTPVideoPacket(SEQ_NUMBER++,0, i, payload);

                    byte[] packetData = rtp.toBytes();

                    DatagramPacket packet = new DatagramPacket(packetData, packetData.length, clientIP, PORT);

                    sending_socket.send(packet);

                    // send voice packet

                    RTPAudioPacket rtpA = new RTPAudioPacket(SEQ_NUMBER++, recorder.getBlock());

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
