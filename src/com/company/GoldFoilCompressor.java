package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GoldFoilCompressor
{

    private static int getColor(int r, int g, int b)
    {
        return (r << 16) | (g << 8) | b;
    }

    public static BufferedImage compress(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();

        for( int i = 0; i < w; i++ )
            for( int j = 0; j < h; j++ )
                img.setRGB(i, j, getColor(255,0,0));

        return img;
    }
}