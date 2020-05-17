package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class DisplayImage
{
    public static void display(BufferedImage img)
    {
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

public class Main {

    public static void main(String[] args)
    {
	    BufferedImage img = new BufferedImage(100,100,1);
	    DisplayImage.display(img);
    }
}
