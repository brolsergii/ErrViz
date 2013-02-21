package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author axel
 */
public class ImageCropper {
    
    static int white = Color.WHITE.getRGB();

    private static int getMin(BufferedImage pict) {
        int width = pict.getWidth();
        int halfheight = pict.getHeight() / 2;
        int min = -1;
        //int white = Color.WHITE.getRGB();

        for (int x = 0; x < halfheight; x++) {
            for (int y = 0; y < width; y++) {
                if ((min == -1) && (pict.getRGB(y, x) != white)) {
                    min = x;
                    break;
                }

            }
        }
        return min;
    }
    
    private static int getMax(BufferedImage pict){
        int width = pict.getWidth();
        int halfheight = pict.getHeight() / 2;
        int max = -1;
        //int white = Color.WHITE.getRGB();

        for (int x = halfheight * 2 -1; x > halfheight; x--) {
            for (int y = 0; y < width; y++) {
                if ((max == -1) && (pict.getRGB(y, x) != white)) {
                    max = x;
                    break;
                }

            }
        }
        return max;
    }

    public static BufferedImage getCroppedImage(BufferedImage aBuf) {
        int min = getMin(aBuf);        
        int max = getMax(aBuf);
        return aBuf.getSubimage(0, min -10 , aBuf.getWidth(), (max - min)+20);
        //to divide computing time by two, just search for min(aBuf) and use commented return instead of the first one
//        int halfheight = aBuf.getHeight() / 2;
//        return aBuf.getSubimage(0, min -10 , aBuf.getWidth(), (halfheight - min + 10) * 2);
    }
}