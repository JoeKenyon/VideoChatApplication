package com.company;

import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args)
    {
        Sender sender = new Sender();
        Receiver receiver = new Receiver();
        Video video = new Video();
        sender.start();
        receiver.start();
        video.start();
    }
}
