package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Sender implements Runnable
{
    static CustomSocket sending_socket;

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
            sending_socket = new CustomSocket();
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
                //Read in a string from the standard input
                byte[] buffer = recorder.getBlock();

                //Make a DatagramPacket from it, with client address and port number
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, PORT);

                //Send it
                sending_socket.send(packet);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
        //Close the socket
        sending_socket.close();
    }
}
