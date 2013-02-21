package dtw;

import java.awt.Color;
import utils.PropertiesHelper;

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
    this.application = PropertiesHelper.getApplication();
  }

  public static Color getColor() {
    return Color.RED;
  }

  @Override
  public String toString() {
//    return "{" + segId + "}Substitution {" + wordRef + "; " + wordHyp + "} possition {" + i + "; " + j + "}";
    return "{" + segId + "}"+application.getString("application.substitution")
            +" {" + wordRef + "; " + wordHyp + "} "
            +application.getString("application.position")+" {" + i + "; " + j + "}";
  }

  @Override
  public String getDetails() {
//    return "Error: Substitution \nSegment id : "
//            + this.segId + "\nSubstitution positions : (" + i + "," + j + ")";
    return application.getString("application.error")+": "+
            application.getString("application.substitution")+
            "\n"+application.getString("application.segmentId")+": "+this.segId +
            "\n"+application.getString("application.substitutionPositions")+": (" + i + "," + j + ")";
  }
}
