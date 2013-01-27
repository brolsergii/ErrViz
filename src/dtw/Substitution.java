package dtw;

import java.awt.Color;

/**
 * @author darion
 */
public class Substitution extends Error {

  public Substitution(String ref, String hyp, int i, int j) {
    this.wordRef = ref;
    this.wordHyp = hyp;
    this.i = i;
    this.j = j;
  }

  public static Color getColor() {
    return Color.RED;
  }

  @Override
  public String toString() {
    return "Substitution {" + wordRef + "; " + wordHyp + "} possition {" + i + "; " + "j}";
  }
}
