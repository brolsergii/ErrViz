package controller;

import java.util.ArrayList;
import model.Word;
import model.CTMFile;
import parserCTM.ParserCTM;

public class CTM {

    private CTM() {
    }
    private static CTM instance;

    public static CTM getInstance() {
        if (instance == null) {
            instance = new CTM();
        }
        return instance;
    }

    public boolean isLoaded() {
        if (ctmFile != null) {
            return true;
        }
        return false;
    }

    public void load(String file) {
        ctmFile = new CTMFile(file);
        ParserCTM pctm = new ParserCTM(ctmFile);
        Thread t = new Thread(pctm);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
        }
    }

    public ArrayList<Word> getWords() {
        return ctmFile.getaWordArray();
    }

    public Word getWordByPossition(int pos) {
        for (Word w : ctmFile.getaWordArray()) {
            if (w.getPosition() <= pos && w.getPosition() + w.getLength() >= pos) {
                return w;
            }
        }
        return null;
    }
    private CTMFile ctmFile;
}
