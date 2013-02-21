package controller;

import java.util.ArrayList;
import model.STMFile;
import model.Sector;
import model.Word;
import parserSTM.ParserSTM;

public class STM {

  private STM() {
  }
  private static STM instance;

  public static STM getInstance() {
    if (instance == null) {
      instance = new STM();
    }
    return instance;
  }

  public boolean isLoaded() {
    if (stmFile != null) {
      return true;
    }
    return false;
  }
  
  public void clear()
  {
    stmFile = null;
  }

  public void load(String file) {
    stmFile = new STMFile(file);
    ParserSTM pstm = new ParserSTM(stmFile);
    Thread t = new Thread(pstm);
    t.start();
    try {
      t.join();
    } catch (InterruptedException e) {
    }
  }

  public ArrayList<Sector> getSectors() {
    return stmFile.getaSectorArray();
  }

  public Word getWordByPossition(int pos) {
    for (Sector s : getSectors()) {
      for (Word w : s.getSentence()) {
        if (w.getPosition() <= pos && w.getPosition() + w.getLength() >= pos) {
          return w;
        }
      }
    }
    return null;
  }

  public Sector getSectorByPossition(int pos) {
    int currentPos = 0;
    boolean lower = true;
    for (int i = 0; i < getSectors().size(); i++) {
      Sector s = getSectors().get(i);
      //for (Sector s : getSectors()) {
      for (Word w : s.getSentence()) {
        if (w.getPosition() <= pos && w.getPosition() + w.getLength() >= pos) {
          return s;
        }
        currentPos = w.getPosition();
        if (currentPos > pos && lower && i > 0) {
          return getSectors().get(i - 1);
        }
      }
    }
    return null;
  }

  public Sector getSectorByID(int id) {
    for (int i = 0; i < getSectors().size(); i++) {
      Sector s = getSectors().get(i);
      if (s.getID() == id) {
        return s;
      }
    }
    return null;
  }

  public int getSectorPositionInTheListByID(int id) {
    for (int i = 0; i < getSectors().size(); i++) {
      Sector s = getSectors().get(i);
      if (s.getID() == id) {
        return i;
      }
    }
    return -1;
  }

  public STMFile getSTMFile() {
    return stmFile;
  }
  private STMFile stmFile;
}
