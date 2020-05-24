package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver implements Runnable
{
    static DatagramSocket receiving_socket;
    static byte[][] received_data;

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

        received_data          = new byte[Utils.SCREEN_HEIGHT][Utils.SCREEN_WIDTH*3];

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

        int packetSize =
                Byte.BYTES     +  // packet type
                Integer.BYTES  +  // sequence number
                Integer.BYTES  +  // chunkX
                Integer.BYTES  +  // chunkY
                Integer.BYTES  +  // payload size
                2000; // payload


        while (running)
        {
            try
            {

                byte[] buffer = new byte[packetSize];

                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);

                receiving_socket.receive(packet);

                // video
                if(buffer[0] == 1)
                {
                    RTPVideoPacket rtp = new RTPVideoPacket(buffer);

                    byte[] payload = GoldFoilCompression.uncompress(rtp.payload);

                    for(int i = 0; i < payload.length; i++)
                    {
                        if(payload[i] != 0)
                            Video.current_frame[rtp.chunkY][i] = payload[i];
                    }
                }

                // audio
                if(buffer[0] == 0)
                {
                    RTPAudioPacket rtp = new RTPAudioPacket(buffer);
                    player.playBlock(GoldFoilCompression.uncompress(rtp.payload));
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        receiving_socket.close();
    }


}

/**
 * send image data, with x and y
 * display chunk recieved, no buffering
 **/