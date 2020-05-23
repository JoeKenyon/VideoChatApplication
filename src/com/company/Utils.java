package com.company;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.imageio.ImageIO;

/**
 * Util Method and Global Static Vars
 */
public class Utils
{
    /**
     * Static variables needed for most Util Methods
     */
    static boolean CAM_USED                  = false;
    static final int SCREEN_WIDTH            = Toolkit.getDefaultToolkit().getScreenSize().width;
    static final int SCREEN_HEIGHT           = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final Rectangle SCREEN_DIMENSIONS = new Rectangle( 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    static Robot robot;

    static {
        robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
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

    /**
     * Describe function here
     */
    public static byte[] uncompress(final byte[] input)
    {
        try
        {
            Inflater decompressor = new Inflater();
            decompressor.setInput(input);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

            byte[] buf = new byte[1024];
            while (!decompressor.finished())
            {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
            bos.close();
            return bos.toByteArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Describe function here
     */
    public static byte[] compress(final byte[] input)
    {
        try
        {
            Deflater compressor = new Deflater();
            compressor.setLevel(Deflater.BEST_COMPRESSION);

            compressor.setInput(input);
            compressor.finish();

            ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

            byte[] buf = new byte[1024];
            while (!compressor.finished())
            {
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
            bos.close();
            return bos.toByteArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new byte[0];
        }
    }


    public static byte[] imageToByteArray(BufferedImage image)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( image, "gif", baos );
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
                BufferedImage screen_shit = robot.createScreenCapture(SCREEN_DIMENSIONS);
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
