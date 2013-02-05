package dtw;

import java.awt.Color;

/**
 * @author sergii
 */
public class Deletion extends Error {

  public Deletion(int segID, String word, int position) {
    this.segId = segID;
    this.wordRef = word;
    this.i = position;
  }
  
  public static Color getColor() {
    return Color.ORANGE;
  }

  @Override
  public String toString() {
    return "{" + segId + "}Deletion {" + wordRef + "} possition {" + i + "}";
  }
}
