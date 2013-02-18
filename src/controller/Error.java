package controller;

import dtw.DTWController;
import java.util.ArrayList;
import javax.naming.SizeLimitExceededException;

/**
 * @author sergii
 */
public class Error {

  private static Error instance;

  public static Error getInstance() {
    if (instance == null) {
      instance = new Error();
    }
    return instance;
  }

  private Error() {
    this.errors = new ArrayList<dtw.Error>();
  }

  public void lookForErrors(ArrayList<model.Sector> ref, ArrayList<model.Sector> hyp) throws SizeLimitExceededException {
    if (!isErrorListEmpty()) {
      return;
    }
    if (ref.size() != hyp.size()) {
      throw new SizeLimitExceededException("REF and HYP sizes don't match");
    }
    for (int i = 0; i < ref.size(); i++) {
      dtw.DTWController dtwc = new DTWController(ref.get(i).getID());
      dtwc.DTWCompare(ref.get(i).getStringSentence(), hyp.get(i).getStringSentence());
      errors.addAll(dtwc.getErrors());
    }
  }
  
  public void clear() {
    this.errors = new ArrayList<dtw.Error>();
    this.current = 0;
  }

  public boolean isErrorListEmpty() {
    return errors.isEmpty();
  }

  public ArrayList<dtw.Error> getErrors() {
    return errors;
  }

  public dtw.Error getCurrentError() {
    return errors.get(current);
  }

  public void goNextError() {
    current++;
    if (current >= errors.size()) {
      current = 0;
    }
  }

  public void goPrevError() {
    current--;
    if (current < 0) {
      current = errors.size() - 1;
    }
  }

  public void setPositionBySectorID(int secID) {
    for (dtw.Error err : errors)
      if (secID >= err.segId)
        current = errors.indexOf(err);
  }
  private int current = 0;
  private ArrayList<dtw.Error> errors;
}
