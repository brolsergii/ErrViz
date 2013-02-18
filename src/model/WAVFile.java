package model;

import audiorender.AudioWaveformCreator;
import com.musicg.graphic.GraphicRender;
import com.musicg.wave.Wave;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 * @author diouf
 * @author anasdridi
 * @author yulia
 */
public class WAVFile {

  private String filePath;
  private String fileName;
  private int imageWidth;
  private int imageHeigh;

  public WAVFile(String filePath) {
    this.filePath = filePath;
    String[] elements = filePath.split(File.separator);
    this.fileName = elements[elements.length - 1];
  }

  public void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

    String filename = filePath;
    String strFilename = filename;
    int BUFFER_SIZE = 128000;
    File soundFile;
    AudioInputStream audioStream;
    AudioFormat audioFormat;
    SourceDataLine sourceLine;

    soundFile = new File(strFilename);


    audioStream = AudioSystem.getAudioInputStream(soundFile);

    audioFormat = audioStream.getFormat();

    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

    sourceLine = (SourceDataLine) AudioSystem.getLine(info);
    sourceLine.open(audioFormat);


    sourceLine.start();

    int nBytesRead = 0;
    byte[] abData = new byte[BUFFER_SIZE];
    while (nBytesRead != -1) {

      nBytesRead = audioStream.read(abData, 0, abData.length);

      if (nBytesRead >= 0) {
        @SuppressWarnings("unused")
        int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
      }
    }

    sourceLine.drain();
    sourceLine.close();
  }


  public BufferedImage generateSonogramme() throws UnsupportedAudioFileException, IOException, Exception {
//    Wave wave = new Wave(filePath);
//
//    System.out.println("Le lengh du wav est : " + wave.length() + "secondes");
//    GraphicRender render = new GraphicRender();
//    BufferedImage image = render.renderWaveform(wave);
//    imageWidth = image.getWidth();
//    imageHeigh = image.getHeight();
//    
//    return image;
      
       AudioWaveformCreator awc;
        
       awc = new AudioWaveformCreator(filePath, "test.png");
       BufferedImage image = awc.createAudioInputStream(10000, 350);
         imageWidth = image.getWidth();
         imageHeigh = image.getHeight();
       return image;
   
  }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeigh() {
        return imageHeigh;
    }

  public int getDureeWav() {
    Wave wave = new Wave(filePath);
    return (int) wave.length();
  }

  public int getTimeCurrentPostion(int positionLarg) {
    int time;
    time = (int) ((this.getDureeWav() * positionLarg) / this.getImageWidth());
    return time;
  }
  
  public int getPositionByTime(int time){
      int position;
      position = (int) ((this.getImageWidth() * time) / this.getDureeWav());
      return position;
  }
}