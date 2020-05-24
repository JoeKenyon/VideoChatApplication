package com.company;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Class for compression
 */
public class GoldFoilCompression
{
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
}
