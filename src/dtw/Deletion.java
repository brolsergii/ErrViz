package dtw;

import java.awt.Color;

/**
 * @author sergii
 */
public class Deletion extends Error {

  public Deletion(String word, int position) {
    this.wordRef = word;
    this.i = position;
  }
  
  public static Color getColor() {
    return Color.ORANGE;
  }

  @Override
  public String toString() {
    return "Deletion {" + wordRef + "} possition {" + i + "}";
  }
}
