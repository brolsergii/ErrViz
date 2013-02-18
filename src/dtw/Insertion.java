package dtw;

import java.awt.Color;

/**
 * @author sergii
 */
public class Insertion extends Error {

  public Insertion(int segID, String word, int position) {
    this.segId = segID;
    this.wordHyp = word;
    this.j = position;
  }

  public static Color getColor() {
    return Color.PINK;
  }

  @Override
  public String toString() {
    return "{" + segId + "}Insertion {" + wordHyp + "} possition {" + j + "}";
  }

  @Override
  public String getDetails() {
    return "Error: Insertion \nSegment id : " + this.segId + "\nInsert word position:" + j;
  }
}
