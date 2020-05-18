package com.company;

import java.io.IOException;
import java.net.*;

public class CustomSocket extends DatagramSocket
{
    public CustomSocket() throws SocketException
    {

    }

    public CustomSocket(int port) throws SocketException
    {
        super(port);
    }

    @Override
    public void receive(DatagramPacket p) throws IOException
    {
        super.receive(p);
    }

    @Override
    public void send(DatagramPacket p) throws IOException
    {
        super.send(p);
    }
}
