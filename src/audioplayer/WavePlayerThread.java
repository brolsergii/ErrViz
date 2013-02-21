package audioplayer;

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

/**
 * @author axel
 * heavily based on fobec's code : http://www.fobec.com/CMS/java/class/jouer-son-musique-format-wav_1043.html
 */
public class WavePlayerThread extends Thread {

  private String filename;
  private final int EXTERNAL_BUFFER_SIZE = 65536; // 16Kb
  private boolean isSuspend = false;
  private boolean isCanceled = false;
  //private VisualPlayer frame = null;
  private int readpercent = 0;
  private double offset = 0;
  //private double stopTime = 0;

  /**
   * Constructeur: fixer le fichier wav
   * @param wavfile
   */
  public WavePlayerThread(String wavfile) {
    filename = wavfile;
  }

  /**
   * Mettre en pause ou reprendre la lecture
   */
  public synchronized void pause() {
    if (this.isSuspend == true) {
      this.isSuspend = false;
    } else {
      this.isSuspend = true;
    }
    notify();
  }

  /**
   * Abandonner la lecture
   */
  public synchronized void cancel() {
    this.isCanceled = true;
    notify();
  }

  public double getFileDuration() {
    File soundFile = new File(filename);
    double duration = 0;
    if (!soundFile.exists()) {
        String error ="Wave file not found: " + filename + "\n";
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, error);
//      System.err.println("Wave file not found: " + filename + "\n");
      duration = 0;
    } else {
      try {
        AudioFormat format = AudioSystem.getAudioInputStream(soundFile).getFormat();
        ///long fileLength = soundFile.length();
        //System.out.println("length : "+fileLength+", framerate : "+format.getFrameRate()+", frameSize : "+format.getFrameSize()+"\n");
        duration = AudioSystem.getAudioInputStream(soundFile).getFrameLength() / (format.getFrameRate());
      } catch (UnsupportedAudioFileException e) {
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e);
//        System.out.println("Unsupported Audio File\n");
        duration = 0;
      } catch (IOException e1) {
          Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e1);
//        System.out.println("IOException\n");
        duration = 0;
      } finally {
        return duration;
      }
    }
    return duration;
  }

  /**
   * Connaitre la progression de lecture
   * @return
   */
  public int getProgress() {
    return this.readpercent;
  }

  public void playFrom(double d) {
    offset = d;
  }

//    public void playSegment(double begin, double end){
//        offset =  begin;
//        stopTime =  end;
//    }
  @Override
  public void run() {
    int offsetInByte = 0;
    //int stopTimeInByte = 0;

    File soundFile = new File(filename);
    if (!soundFile.exists()) {
        String error = "Wave file not found: " + filename;
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, error);
//      System.err.println("Wave file not found: " + filename);
      return;
    }

    AudioInputStream audioInputStream = null;
    try {
      audioInputStream = AudioSystem.getAudioInputStream(soundFile);
    } catch (UnsupportedAudioFileException e1) {
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e1);
//      e1.printStackTrace();
      return;
    } catch (IOException e1) {
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e1);
//      e1.printStackTrace();
      return;
    }

    AudioFormat format = audioInputStream.getFormat();
    SourceDataLine auline = null;
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

    try {
      auline = (SourceDataLine) AudioSystem.getLine(info);
      auline.open(format);
    } catch (LineUnavailableException e) {
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e);
//      e.printStackTrace();
      return;
    } catch (Exception e) {
        Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e);
//      e.printStackTrace();
      return;
    }

    auline.start();
    int nBytesRead = 0;
    //longueur du fichier wav
    Long audiolength = soundFile.length();
    int audioreaded = 0;

    offsetInByte = (int) (offset * (format.getFrameRate() * format.getFrameSize()));
    //stopTimeInByte = (int)(stopTime * (format.getFrameRate()*format.getFrameSize()));
    String in = "offsetInByte  : " + offsetInByte + "\n";
    Logger.getLogger(WavePlayerThread.class.getName()).log(Level.INFO, in);
//    System.out.println("offsetInByte  : " + offsetInByte + "\n");
    int nLoopsOffset = offsetInByte / EXTERNAL_BUFFER_SIZE;
    int remainderOffset = offsetInByte % EXTERNAL_BUFFER_SIZE; //<16kb

    byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

    try {
      while (nBytesRead != -1 && this.isCanceled == false) {
        if (offsetInByte != 0) {
          for (int i = 0; i < nLoopsOffset; i++) {
            nBytesRead = audioInputStream.read(abData, 0, abData.length);
            audioreaded += nBytesRead;
          }
          nBytesRead = audioInputStream.read(abData, 0, remainderOffset);
          audioreaded += nBytesRead;
          readpercent = Math.round(audioreaded * 100 / audiolength);
          offsetInByte = 0;
          //en principe, bonne position obtenue
        }
        nBytesRead = audioInputStream.read(abData, 0, abData.length);
        if (nBytesRead >= 0) {
          //calcul de la progression
          readpercent = Math.round(audioreaded * 100 / audiolength);
          auline.write(abData, 0, nBytesRead);
          audioreaded += nBytesRead;
        }
        /**
         * Annuler la lecture
         */
        if (this.isCanceled == true) {
           Logger.getLogger(WavePlayerThread.class.getName()).log(Level.INFO, "Thread has been stopped"); 
//          System.out.print("Thread has been stopped");
          break;
        }
        /**
         * Mettre en pause
         */
        if (this.isSuspend == true) {
          while (this.isSuspend == true) {
            try {
              Thread.sleep(250);
              if (this.isCanceled == true) {
                 Logger.getLogger(WavePlayerThread.class.getName()).log(Level.INFO, "Thread has been stopped"); 
//                System.out.print("Thread has been stopped");
                break;
              }
            } catch (InterruptedException ex) {
                 Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, ex);
//              ex.printStackTrace();
            }
          }
        }
      }
    } catch (IOException e) {
         Logger.getLogger(WavePlayerThread.class.getName()).log(Level.SEVERE, null, e);
//      e.printStackTrace();
      return;
    } finally {
      auline.drain();
      auline.close();
    }
    /**
     * Fin du thread de lecture
     */
    //this.frame.onTerminated();
  }
}