package com.company;

import java.awt.image.BufferedImage;

public class Main {

    //ethans static String SEND_ADDR = "192.168.1.148"
    //joe    static String SEND_ADDR = "192.168.1.109"
    static String SEND_ADDR = "192.168.1.148";

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
