package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender implements Runnable
{
    static DatagramSocket sending_socket;
    static int SEQ_NUMBER = 0;
    static byte[] PREV_FRAME;

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



                //Make a DatagramPacket from it, with client address and port number
                //DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, PORT);

                //Send it
                //sending_socket.send(packet);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        //Close the socket
        sending_socket.close();
    }

    /**
     *
     * formPacketBuffer
     */
    public static byte[][] formPacketBuffer(byte[] image_data)
    {
        // get video data
        byte[] video_data = Utils.getVideoData();
        assert video_data != null;

        // put into chunks
        byte[][] chunks = new byte[3][3];

        byte[] compressed = Utils.compress(video_data);
        System.out.println(compressed.length);


        // check similarities with previous frame
        // save previous frame as current frame
        PREV_FRAME = video_data;

        //for(int i = 0; i < chunks.length; i++)
        {
            // get sequence number
            int seq_num = SEQ_NUMBER++;
        }

        // compress it

        // send it

        return new byte[2][2];

    }
}
