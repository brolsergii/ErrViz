package dtw;

import java.awt.Color;

/**
 * @author darion
 */
public class Substitution extends Error {

  public Substitution(String ref, String hyp, int i, int j) {
    this.color = Color.RED;
    this.wordRef = ref;
    this.wordHyp = hyp;
    this.i = i;
    this.j = j;
  }

  @Override
  public String toString() {
    return "Substitution {" + wordRef + "; " + wordHyp + "} possition {" + i + "; " + "j}";
  }
}
