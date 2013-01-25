package dtw;

import java.awt.Color;

/**
 * @author sergii
 */
public class Insertion extends Error {

  public Insertion(String word, int position) {
    this.color = Color.PINK;
    this.wordHyp = word;
    this.j = position;
  }

  @Override
  public String toString() {
    return "Insertion {" + wordHyp + "} possition {" + j + "}";
  }
}
