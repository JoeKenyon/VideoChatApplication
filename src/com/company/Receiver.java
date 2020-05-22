package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Random;

public class Receiver implements Runnable
{
    static CustomSocket receiving_socket;

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
            receiving_socket = new CustomSocket(PORT);
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
                byte[] buffer = new byte[512];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);

                receiving_socket.receive(packet);

                // process data
                buffer = Utils.processAudioData(buffer);

                player.playBlock(buffer);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        receiving_socket.close();
    }


}
