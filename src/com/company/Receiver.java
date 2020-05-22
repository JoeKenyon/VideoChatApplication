package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class Receiver implements Runnable
{
    static DatagramSocket receiving_socket;

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
        AudioPlayer player     = null;

        try
        {
            player           = new AudioPlayer();
            receiving_socket = new DatagramSocket(PORT);
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
                byte[] buffer = new byte[Sender.PACKET_SIZE];

                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);

                receiving_socket.receive(packet);

                ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData());

                //seq num
                int seq_num   = byteBuffer.getInt();

                // block num
                int block_num = byteBuffer.getInt();

                // start of image
                if(block_num == 0)
                {
                    // num of blocks
                    int num_of_blocks = byteBuffer.getInt();

                    // byte buffer for full image
                    ByteBuffer full_image = ByteBuffer.allocate(Sender.SIZE_OF_CHUNKS * num_of_blocks);


                    // get first video chunk
                    byte[] video_chunk = new byte[Sender.SIZE_OF_CHUNKS];
                    byteBuffer.get(video_chunk, 0, video_chunk.length);

                    // add it to full image
                    full_image.put(video_chunk);

                    for(int i = 0; i < num_of_blocks-1; i++)
                    {
                        // get another packet
                        receiving_socket.receive(packet);

                        // create a byte buffer from its data
                        ByteBuffer b = ByteBuffer.wrap(packet.getData());

                        // extract data's
                        seq_num   = b.getInt();
                        block_num = b.getInt();

                        b.get(video_chunk, 0, video_chunk.length);

                        // add it to full image
                        full_image.put(video_chunk);
                    }

                    Video.current_frame = Utils.toImage(full_image.array(), 750, 500);
                }

                // video chunk


                //System.out.println("R: "+ seq_num + "," + block_num + "," + num_of_blocks);
                //System.out.println("R: "+ Arrays.toString(video_chunk));

                //player.playBlock(buffer);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        receiving_socket.close();
    }


}
