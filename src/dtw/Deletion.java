package dtw;

import java.awt.Color;

/**
 * @author
 */
public class Deletion extends Error {

  public Deletion(String word, int position) {
    this.color = Color.ORANGE;
    this.wordRef = word;
    this.i = position;
  }

  @Override
  public String toString() {
    return "Deletion {" + wordRef + "} possition {" + i + "}";
  }
}
