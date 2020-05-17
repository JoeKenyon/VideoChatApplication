package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public static void main(String[] args) throws IOException
    {
        //BufferedImage img = ImageIO.read(new File("something.jpg"));
	    //img = GoldFoilCompressor.compress(img);
	    //DisplayImage.display(img);

        Sender sender;
        Receiver receiver;
        sender.start();
        receiver.start();
    }
}
