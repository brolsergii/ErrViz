package dtw;

import java.awt.Color;
import utils.PropertiesHelper;

/**
 * @author sergii
 */
public class Deletion extends Error {

  public Deletion(int segID, String word, int position) {
    this.segId = segID;
    this.wordRef = word;
    this.i = position;
    this.application = PropertiesHelper.getApplication();
  }

  public static Color getColor() {
    return Color.ORANGE;
  }

  @Override
  public String toString() {
//    return "{" + segId + "}Deletion {" + wordRef + "} possition {" + i + "}";
    return "{" + segId + "}"+application.getString("application.deletion")+" {" + wordRef + "} "+application.getString("application.position")+" {" + i + "}";

  }

  @Override
  public String getDetails() {
//    return "Error: Deletion \nSegment id : " + this.segId + "\nDeleted word position:" + i;
    return application.getString("application.error")+": "
            +application.getString("application.deletion")+
            "\n"+application.getString("application.segmentId")+": "+this.segId +
            "\n"+application.getString("application.deletionPosition")+": "+ i;
  }
}
