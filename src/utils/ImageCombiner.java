package utils;

import java.awt.image.BufferedImage;

/**
 *
 * @author Axel Henry <axel.durandil@gmail.com>
 */
public class ImageCombiner {
    /**
     * Returns the resulting image of the concatenation of img1 and img2
     * @param img1 the first image
     * @param img2 the second image
     * @return the resulting image
     */
    public static BufferedImage combineImages(BufferedImage img1,BufferedImage img2){
      int widthImg1 = img1.getWidth();
      int widthImg2 = img2.getWidth();
      int heightImg1 = img1.getHeight();
      int heightImg2 =img2.getHeight();
      BufferedImage out = new BufferedImage(widthImg1+widthImg2,heightImg1 > heightImg2 ? heightImg1 : heightImg2,BufferedImage.TYPE_INT_RGB);
      out.createGraphics().drawImage(img1, 0, 0,null);
      out.createGraphics().drawImage(img2, widthImg1, 0, null);
      return out;
    }
    
}
