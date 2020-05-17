package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class DisplayImage
{
    public static void display(BufferedImage img)
    {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(500, 500);
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
	    BufferedImage img = new BufferedImage(500,500,1);
	    GoldFoilCompressor.compress(img);
	    DisplayImage.display(img);
    }
}
