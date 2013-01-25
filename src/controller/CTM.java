package controller;

import java.util.ArrayList;
import model.Word;
import model.Sector;
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

  public ArrayList<Sector> getSectors() {
    return ctmFile.getSectorArray();
  }

  public void doSegmentation() {
    ctmFile.doSegmentation(STM.getInstance().getSTMFile());
  }

  public Word getWordByPossition(int pos) {
    if (ctmFile != null) {
      for (Sector sec : ctmFile.getSectorArray()) {
        for (Word w : sec.getSentence()) {
          if (w.getPosition() <= pos && w.getPosition() + w.getLength() >= pos) {
            return w;
          }
        }
      }
    } else {
      for (Word w : ctmFile.getaWordArray()) {
        if (w.getPosition() <= pos && w.getPosition() + w.getLength() >= pos) {
          return w;
        }
      }
    }
    return null;
  }

  public Sector getSectorByWord(Word wrd) {
    if (ctmFile != null) {
      return ctmFile.getSectorByWord(wrd);
    }
    return null;
  }
  private CTMFile ctmFile;
}
