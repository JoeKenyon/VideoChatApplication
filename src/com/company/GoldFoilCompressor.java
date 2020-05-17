package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.*;

public class GoldFoilCompressor
{

    private static int getColor(int r, int g, int b)
    {
        return (r << 16) | (g << 8) | b;
    }

    private static byte[] toByteArray(BufferedImage img){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try { ImageIO.write(img, "jpg", baos);}
        catch (IOException e) { e.printStackTrace(); }
        return baos.toByteArray();
    }

    private static BufferedImage toBufferedImage(byte[] bites)
    {
        InputStream in = new ByteArrayInputStream(bites);
        try {
            BufferedImage bImageFromConvert = ImageIO.read(in);
            return bImageFromConvert;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] compress(final byte[] data)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        byte[] compressed = null;
        try
        {
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.close();
            compressed = bos.toByteArray();
            bos.close();
        } catch (IOException e) { e.printStackTrace(); }
        return compressed;
    }

    private static byte[] decompress(final byte[] input) {
        ByteArrayInputStream bytein = new java.io.ByteArrayInputStream(input);
        GZIPInputStream gzin = null;
        try {
            gzin = new GZIPInputStream(bytein);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteout = new java.io.ByteArrayOutputStream();

        int res = 0;
        byte buf[] = new byte[1024];
        while (res >= 0) {
            try {
                res = gzin.read(buf, 0, buf.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (res > 0) {
                byteout.write(buf, 0, res);
            }
        }
        return byteout.toByteArray();
    }

    public static BufferedImage compress(BufferedImage img)
    {
        byte[] bites = toByteArray(img);
        System.out.println("Original: " + bites.length  );

        img = toBufferedImage(bites);
        for (int x = 0; x < img.getWidth(); x++)
        {
            for (int y = 0; y < img.getHeight(); y++)
            {
                int new_x = (x / 5) * 5;
                int new_y = (y / 5) * 5;
                img.setRGB(x, y, img.getRGB(new_x,new_y));
            }
        }
        bites = toByteArray(img);
        byte[] compressedData = compress(bites);
        System.out.println("Compressed: " + compressedData.length );

        return toBufferedImage(decompress(compressedData));
    }
}