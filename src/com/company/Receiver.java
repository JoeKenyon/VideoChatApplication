package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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

                player.playBlock(generateSineWave(120));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        receiving_socket.close();
    }

    private static byte[] generateSineWave(int frequencyOfSignal)
    {
        float sampleRate = 8000;
        double f = frequencyOfSignal;
        double a = .5;
        double twoPiF = 2*Math.PI*f;

        double[] buffer = new double [8000];
        for (int sample = 0; sample < buffer.length; sample++)
        {
            double time = sample / sampleRate;
            buffer[sample++] = a * Math.sin(twoPiF*time);
        }

        byte[] byteBuffer = new byte[8000];
        int idx = 0;
        for (int i = 0; i < byteBuffer.length; )
        {
            int x = (int) (buffer[idx++]*127);
            byteBuffer[i++] = (byte) x;
        }
        return byteBuffer;
    }
}
