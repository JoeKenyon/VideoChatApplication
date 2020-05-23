package com.company;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Random;
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
    static ByteArrayOutputStream baos        = new ByteArrayOutputStream();
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
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

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

    /**
     * Describe function here
     */
    public static byte[] imageToByteArray(BufferedImage img)
    {
        img = resizeImage(img, 750, 500);
        try
        {
            return ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Describe function here
     */
    public static void drawImageSegment(Graphics g, byte[] imageSegment, int chunkX, int chunkY)
    {
        BufferedImage img = new BufferedImage(750, 1, BufferedImage.TYPE_3BYTE_BGR);
        img.setData(Raster.createRaster(img.getSampleModel(), new DataBufferByte(imageSegment, imageSegment.length), new Point()));
        g.drawImage(img, chunkX,chunkY,null);
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
                //BufferedImage screen_shit =
                return imageToByteArray(robot.createScreenCapture(SCREEN_DIMENSIONS));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

