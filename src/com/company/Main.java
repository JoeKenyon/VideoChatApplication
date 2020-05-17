package com.company;

import javax.swing.*;
import java.awt.image.BufferedImage;

class DisplayImage
{
    public static void display(BufferedImage img)
    {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(img.getWidth(), img.getHeight());
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        lbl.setBounds(0,0,img.getWidth(),img.getHeight());
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

public class Main {

    public static void main(String[] args)
    {
        Sender sender = new Sender();
        Receiver receiver = new Receiver();
        sender.start();
        receiver.start();
    }
}
