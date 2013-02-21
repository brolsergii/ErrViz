package dtw;

import java.awt.Color;
import utils.PropertiesHelper;

/**
 * @author sergii
 */
public class Insertion extends Error {

  public Insertion(int segID, String word, int position) {
    this.segId = segID;
    this.wordHyp = word;
    this.j = position;
    this.application = PropertiesHelper.getApplication();
  }

  public static Color getColor() {
    return Color.PINK;
  }

  @Override
  public String toString() {
//    return "{" + segId + "}Insertion {" + wordHyp + "} possition {" + j + "}";
    return "{" + segId + "}"+application.getString("application.insertion")+" {" + wordHyp + "} "+application.getString("application.position")+" {" + j + "}";
  }

  @Override
  public String getDetails() {
//    return "Error: Insertion \nSegment id : " + this.segId + "\nInsert word position:" + j;
    return application.getString("application.error")+": "
            +application.getString("application.insertion")+
            "\n"+application.getString("application.segmentId")+": "+this.segId +
            "\n"+application.getString("application.insertionPosition")+": " + j;
  }
}
