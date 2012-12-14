/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.musicg.graphic.GraphicRender;
import com.musicg.wave.Wave;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author anasdridi
 */
public class WAVFile {
    private String filePath;
    private String fileName;
    private String outFolder;

    public WAVFile(String filePath) {
        this.filePath = filePath;
        String [] elements = filePath.split("/");
        this.fileName = elements[elements.length-1];
        outFolder = "ressources/images/";
    }
    
    
    
     public void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException{

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
     
 
     
     
     
     
     
        public String generateSonogramme()
        {
            
            
            
            
                Wave wave = new Wave(filePath);

		// print the wave header and info
		
        
                // Graphic render
                GraphicRender render=new GraphicRender();
                //render.setHorizontalMarker(1);
                //render.setVerticalMarker(1);

                render.renderWaveform(wave, outFolder+fileName+".jpg");

             
            
            return outFolder+fileName+".jpg";
        }
    
    
    
}
