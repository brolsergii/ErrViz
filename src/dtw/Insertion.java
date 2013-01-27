package dtw;

import java.awt.Color;

/**
 * @author sergii
 */
public class Insertion extends Error {

  public Insertion(String word, int position) {
    this.wordHyp = word;
    this.j = position;
  }

  public static Color getColor() {
    return Color.PINK;
  }

  @Override
  public String toString() {
    return "Insertion {" + wordHyp + "} possition {" + j + "}";
  }
}
