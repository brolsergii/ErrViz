package model;

import com.musicg.graphic.GraphicRender;
import com.musicg.wave.Wave;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
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
 */
public class WAVFile {

  private String filePath;
  private String fileName;
  private String outFolder;
  private String imgPath;

  public WAVFile(String filePath) {
    this.filePath = filePath;
    String[] elements = filePath.split(File.separator);
    this.fileName = elements[elements.length - 1];
    imgPath = "";
    outFolder = "ressources" + File.separator; // TODO: may be a better solution
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

  public String generateSonogramme() {
    Wave wave = new Wave(filePath);

    System.out.println("Le lengh du wav est : " + wave.length() + "secondes");

// print the wave header and info

    // Graphic render
    GraphicRender render = new GraphicRender();
    //render.setHorizontalMarker(1);
    //render.setVerticalMarker(1);
    //render.setHorizontalMarker(300);
    //render.setVerticalMarker(700);

    render.renderWaveform(wave, outFolder + fileName + ".jpg");


    imgPath = outFolder + fileName + ".jpg";
    return outFolder + fileName + ".jpg";
  }

    public String getImgPath() {
        return imgPath;
    }
  
  

  public Image scaleImage(Image source) {
    Image imi = (new ImageIcon(source)).getImage();
    return imi;
  }

  public int getImageWidth() {
    Image imi = (new ImageIcon(imgPath)).getImage();
    int largeur = imi.getWidth(null);
    return largeur;
  }

  public int getImageHeigh() {
    Image imi = (new ImageIcon(imgPath)).getImage();
    int largeur = imi.getHeight(null);
    return largeur;
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