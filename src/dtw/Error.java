package dtw;

import java.util.ResourceBundle;

/**
 * @author sergii
 */
public abstract class Error {
  public int segId;
  public int i;
  public int j;
  public String wordRef;
  public String wordHyp;
  public abstract String getDetails();
  public ResourceBundle application;
}
