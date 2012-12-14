package controller;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.WAVFile;
public class WAV {

    private WAV() {
    }
    private static WAV instance;

    public static WAV getInstance() {
        if (instance == null) {
            instance = new WAV();
        }
        return instance;
    }

    public boolean isLoaded() {
        if (wavFile != null) {
            return true;
        }
        return false;
    }

    public void load(String file) {
        wavFile = new WAVFile(file);
    }
    
    public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        wavFile.playSound();
    }
    
    public String getSonogramme(){
        return wavFile.generateSonogramme();
    }

    public WAVFile getWavFile() {
        return wavFile;
    }
    
    

    
    private WAVFile wavFile;
}
