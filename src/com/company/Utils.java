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
    static boolean CAM_USED                   = false;
    static final int SCREEN_WIDTH             = Toolkit.getDefaultToolkit().getScreenSize().width;
    static final int SCREEN_HEIGHT            = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final Rectangle SCREEN_DIMENSIONS  = new Rectangle( 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    static Robot _ROBOT;

    static
    {
        try { _ROBOT = new Robot(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * If CAM_USED, capture webcam.
     * Otherwise, get desktop screen
     */
    public static byte[] getVideoData()
    {
        if (CAM_USED)
        {
            return null;
        }
        else
        {
            // resize the screenshot to fit our GUI size
            BufferedImage FRAME = resizeImage(_ROBOT.createScreenCapture(SCREEN_DIMENSIONS), 750, 500);

            // return its byte representation
            return ((DataBufferByte) FRAME.getRaster().getDataBuffer()).getData();
        }

    }

    /**
     * Describe function here
     */
    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH)
    {
        // get scaled instance of image
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_FAST);

        // create buffered image to return
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

        // create a 2Dgraphics object from the new buffered image
        Graphics2D g2d = dimg.createGraphics();

        // draw the scaled image on the new buffered image
        g2d.drawImage(tmp, 0, 0, null);

        // dispose, memory management i guess
        g2d.dispose();

        return dimg;
    }

    /**
     * Describe function here
     */
    public static void drawImageSegment(Graphics g, byte[] imageSegment, int chunkX, int chunkY)
    {
        // create buffered image to store our bytes
        BufferedImage img = new BufferedImage(750, 1, BufferedImage.TYPE_3BYTE_BGR);

        // set the buffered image's data
        img.setData(Raster.createRaster(img.getSampleModel(), new DataBufferByte(imageSegment, imageSegment.length), new Point()));

        // draw it to our graphics object
        g.drawImage(img, chunkX,chunkY,null);
    }
}

