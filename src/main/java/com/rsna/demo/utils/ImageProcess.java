package com.rsna.demo.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;


public class ImageProcess {
    //TODO 传入Image 参数 img
    static float  []mean = {123, 117, 104};
    public static float[][][][] readImage(File file){
        //File file = new File("./data/3.jpg");
        BufferedImage bufImg = new BufferedImage(300,300,BufferedImage.TYPE_BYTE_GRAY);
        Image img;
        try {
            img = ImageIO.read(file);
            Graphics graphics=bufImg.getGraphics();
            graphics.drawImage(img,0,0,300,300,null);
            //ImageIO.write(bufImg,"jpg",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Raster raster = bufImg.getData();
        int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
        int [] pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
        float[][][][] res = new float[1][300][300][3];
        for (int c = 0; c < 3; c++)
            for (int w = 0; w < 300; w++)
            {
                for (int h = 0; h <300; h++)
                {
                    res[0][w][h][c]=pixels[w*300+h];
                }

            }
        return res;
    }
}