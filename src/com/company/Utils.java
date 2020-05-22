package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

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
                return ((DataBufferByte) screen_shit.getData().getDataBuffer()).getData();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
