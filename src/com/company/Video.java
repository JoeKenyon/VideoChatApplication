package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Video implements Runnable
{
    static BufferedImage current_frame;

    static class VideoWindow extends JPanel
    {
        protected Timer timer;
        public VideoWindow()
        {
            this.setLayout(null);
            timer = new Timer(40, e -> repaint());
            timer.start();
        }
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if(current_frame != null)
            {
                g.drawImage(current_frame, 100, 0, null);
            }
            else
            {
                g.setColor(Color.black);
                g.fillRect(0, 0, 750, 500);
                g.setColor(Color.white);
                g.drawString("NO DATA RECEIVED", 750/2, 500/2);
            }
        }
    }

    void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        JFrame frame = new JFrame();
        frame.setTitle("HEY BEATCH");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBounds(0,0,750,500);
        frame.setContentPane(new VideoWindow());
        frame.setVisible(true);
    }
}