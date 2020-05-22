package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Utils
{
    /**
     * Static variables needed for most Util Methods
     */
    static boolean CAM_USED                  = false;
    static final int SCREEN_WIDTH            = Toolkit.getDefaultToolkit().getScreenSize().width;
    static final int SCREEN_HEIGHT           = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final Rectangle SCREEN_DIMENSIONS = new Rectangle( 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    /**
     * Describe function here
     */
    public static byte[] processAudioData(byte[] data)
    {
        byte value = data[0];

        for (int i=1; i < data.length; i++)
        {
            byte currentValue = data[i];
            value += (currentValue - value);
            data[i] = value;
        }

        return data;
    }

    /**
     * Describe function here
     */
    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static byte[] createPacketBuffer(int seq_num, byte[] image)
    {
        return new byte[2];
    }

    /**
     * Describe function here
     */
    public static byte[] imageToByteArray(BufferedImage image)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( image, "jpg", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Describe function here
     */
    public static byte[] getVideoData()
    {
        try
        {
            if (CAM_USED)
            {
                // return users camera data instead
                return null;
            }
            else
            {
                // return users screen instead
                BufferedImage screen_shit = new Robot().createScreenCapture(SCREEN_DIMENSIONS);
                return imageToByteArray(screen_shit);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
