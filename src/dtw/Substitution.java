package dtw;

import java.awt.Color;

/**
 * @author darion
 */
public class Substitution extends Error {

  public Substitution(int segID, String ref, String hyp, int i, int j) {
    this.segId = segID;
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
    return "{" + segId + "}Substitution {" + wordRef + "; " + wordHyp + "} possition {" + i + "; " + j + "}";
  }

  @Override
  public String getDetails() {
    return "Error: Substitution \nSegment id : "
            + this.segId + "\nSubstitution positions : (" + i + "," + j + ")";
  }
}
