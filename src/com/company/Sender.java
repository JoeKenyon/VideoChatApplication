package com.company;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Sender implements Runnable
{
    static DatagramSocket sending_socket;
    static byte[] PREV_FRAME;
    static int SEQ_NUMBER     = 0;
    static int SIZE_OF_CHUNKS = 29;
    static int HEADER_SIZE    = Integer.BYTES*4;
    static int PACKET_SIZE    = HEADER_SIZE + SIZE_OF_CHUNKS;

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


                byte[][] chunks = formPacketBuffer();


                ByteBuffer full_image = ByteBuffer.allocate(SIZE_OF_CHUNKS * chunks.length);

                for(int i = 0; i < chunks.length; i++)
                {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_SIZE);
                    // seq num
                    byteBuffer.putInt(SEQ_NUMBER++);
                    // block num
                    byteBuffer.putInt(i);
                    // num of blocks
                    byteBuffer.putInt(chunks.length);
                    // video chunk
                    byteBuffer.put(chunks[i]);

                    byte[] buffer = byteBuffer.array();

                    //System.out.println(Arrays.toString(buffer));
                    //System.out.println(Arrays.toString(chunks[i]));

                    // oh i got it
                    ByteBuffer b = ByteBuffer.wrap(buffer);
                    int seq_num   = b.getInt();
                    int block_num = b.getInt();
                    int num_of_blocks = b.getInt();
                    // get first video chunk
                    byte[] video_chunk = new byte[Sender.SIZE_OF_CHUNKS];
                    b.get(video_chunk, 0, video_chunk.length);

                    //video_chunk = Utils.uncompress(video_chunk);

                    //for(int j = 0; j < video_chunk.length; j++)
                    {
                        //stuff
                    }

                    full_image.put(video_chunk);

                    //Make a DatagramPacket from it, with client address and port number
                    //DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, PORT);

                    //Send it
                   // sending_socket.send(packet);
                }

                Video.current_frame = Utils.toImage(full_image.array(), 750, 500);




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
