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
                RTPAudioPacket rtp = new RTPAudioPacket(SEQ_NUMBER++, 512, recorder.getBlock());
                byte[] packetData = rtp.toBytes();
                DatagramPacket packet = new DatagramPacket(packetData, packetData.length, clientIP, PORT);

                //Send it
                sending_socket.send(packet);

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
    public static byte[][] formPacketBuffer()
    {
        // get video data
        byte[] video_data = Utils.getVideoData();
        assert video_data != null;

        byte[] temp = video_data;

        if(PREV_FRAME != null)
            for(int i = 0; i < video_data.length ; i++ )
            {
                if(PREV_FRAME.length > i)
                    if(PREV_FRAME[i] == video_data[i])
                        video_data[i] = 0;
            }

        //PREV_FRAME = temp;

        //video_data = //Utils.compress(video_data);

        byte[][] chunks = divideArray(video_data, SIZE_OF_CHUNKS);

        return chunks;

    }

    public static byte[][] divideArray(byte[] source, int chunksize)
    {
        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];

        int start = 0;

        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
            start += chunksize ;
        }

        return ret;
    }
}
