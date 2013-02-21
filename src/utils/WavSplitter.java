package utils;

import audiorender.AudioWaveformCreator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Axel Henry <axel.durandil@gmail.com>
 */
public class WavSplitter {

    /**
     * Function to generate a waveform of a large file by splitting into smaller pieces
     * @param wavToSplit the wav file to split
     * @param nbSplit the number of times that we split our file
     * @return the resulting image
     */
    public static BufferedImage split(File wavToSplit, int nbSplit){
        AudioInputStream audioInputStream = null;
        BufferedImage b =null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(wavToSplit);
            long frames = audioInputStream.getFrameLength();
            long[] splits = new long[nbSplit];

            for (int i = 0; i < nbSplit - 1; i++) {
                splits[i] = frames / nbSplit;
            }
            splits[nbSplit - 1] = frames;
            int sum = 0;
            for (int i = 0; i < nbSplit - 1; i++) {
                sum += splits[i];
            }
            splits[nbSplit - 1] -= sum;
            for (int i = 0; i < splits.length; i++) {
                byte[] data = new byte[(int) splits[i] * audioInputStream.getFormat().getFrameSize()];

                int nBytesRead = 0;
                int counter = 0;
                while ((nBytesRead != -1) && (counter < data.length)) {
                    nBytesRead = audioInputStream.read(data, counter, data.length - counter);
                    if (nBytesRead != -1) {
                        counter += nBytesRead;
                    }
                }
                
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                AudioInputStream ais = new AudioInputStream(bais, audioInputStream.getFormat(), data.length / audioInputStream.getFormat().getFrameSize());

                AudioWaveformCreator awc = new AudioWaveformCreator();

                try {
                    BufferedImage tmp = awc.createSplittedAudioInputStream(ais, 10000 / splits.length, 350);
                    if(b!=null){
                        b = ImageCombiner.combineImages(b, tmp);
                    }
                    else{
                        b=tmp;
                    }
                    
                } catch (Exception e) {
                    Logger.getLogger(WavSplitter.class.getName()).log(Level.SEVERE, null, e);
//                    e.printStackTrace();
                }
            }

        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(WavSplitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WavSplitter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(WavSplitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ImageCropper.getCroppedImage(b.getSubimage(1, 0, b.getWidth() - 1, b.getHeight()));
    }
}
