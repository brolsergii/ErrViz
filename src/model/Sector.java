package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Sector {

  private ArrayList<Word> sentence;
  //private String sentence;
  private float begin;
  private float end;
  private Speaker aSpeaker;
  private String conditions;
  private int ID;

  public Sector(String s, float f1, float f2, Speaker spk, String cond, int offset) {
    //public Sector(float f1, float f2, Speaker spk, String cond ){
    sentence = new ArrayList<Word>();
    String[] arrayTmp = s.split(" ");
    for (int i = 0; i < arrayTmp.length; i++) {
      sentence.add(new Word(arrayTmp[i], i + offset));
    }
    //sentence = s;
    begin = f1;
    end = f2;
    aSpeaker = spk;
    conditions = cond;
    this.ID = getUID();
  }

  public Sector(ArrayList<Word> words, float f1, float f2, Speaker spk, String cond, int offset) {
    sentence = (ArrayList<Word>) words.clone();
    begin = f1;
    end = f2;
    aSpeaker = spk;
    conditions = cond;
    this.ID = getUID();
  }

  public Speaker getaSpeaker() {
    return aSpeaker;
  }

  public void setSpeaker(Speaker aSpeaker) {
    this.aSpeaker = aSpeaker;
  }

  public int getLength() {
    return sentence.size();
  }

  public int getLengthInChars() {
    int size = 0;
    for (Word w : getSentence()) {
      size += w.getLength() + 1;
    }
    size--;
    return size;
  }

  public int getPosition() {
    int pos = Integer.MAX_VALUE;
    for (Word w : getSentence()) {
      if (w.getPosition() < pos) {
        pos = w.getPosition();
      }
    }
    return pos;
  }

  public int getID() {
    return ID;
  }

  public float getBegin() {
    return begin;
  }

  public void setBegin(float begin) {
    this.begin = begin;
  }

  public String getConditions() {
    return conditions;
  }

  public void setConditions(String conditions) {
    this.conditions = conditions;
  }

  public float getEnd() {
    return end;
  }

  public void setEnd(float end) {
    this.end = end;
  }

  public ArrayList<Word> getSentence() {
    return sentence;
  }
  
  public String getStringSentence() {
    String res = "";
    for (Word wrd : getSentence())
      res += wrd.getWord() + " ";
    return res.trim();
  }

  /**
   * Get the word from the sector by it's position.
   * @param pos - global position of the word.
   * @param offset - offset of the segment.
   * @return the Word if any
   */
  public Word getWordByPossition(int pos) {
    for (Word w : sentence) {
      if (w.getPosition() <= pos && w.getPosition() + w.getLength() >= pos) {
        return w;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    Iterator<Word> itr = sentence.iterator();
    StringBuilder str = new StringBuilder();
    while (itr.hasNext()) {
      str.append(itr.next().toString());
    }
    return str.toString();
  }
  private static int UID = 0;

  private static int getUID() {
    UID++;
    return UID;
  }
}
