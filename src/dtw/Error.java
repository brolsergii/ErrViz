package dtw;

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
}
