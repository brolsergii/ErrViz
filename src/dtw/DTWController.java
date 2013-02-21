package dtw;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertiesHelper;

/**
 * @author sergii
 */
public class DTWController {

  public DTWController(int segID) {
    application = PropertiesHelper.getApplication();
    this.segInd = segID;
  }

  private void insertion(String str, int number) {
    errors.add(new Insertion(segInd, str, number));
  }

  private void deletion(String str, int number) {
    errors.add(new Deletion(segInd, str, number));
  }

  private void match(String ref, String hyp, int i, int j) {
    if (!ref.equals(hyp)) {
      errors.add(new Substitution(segInd, ref, hyp, i, j));
    }
  }

  /* Return a list of found errors in while comparing two 
   * sentences. Is empty untill DTWCompare method was called
   */
  public ArrayList<Error> getErrors() {
    return errors;
  }

  public char[][] getAnalisysMatrix() {
    return DTWMatchesChar;
  }

  public void DTWCompare(String phraseRef, String phraseHyp) {

    //System.out.println("REF: " + phraseRef);
      Logger.getLogger(DTWController.class.getName()).log(Level.INFO, application.getString("application.reference") + phraseRef);
//    System.out.println(application.getString("application.reference") + phraseRef);
    //System.out.println("HYP: " + phraseHyp);
    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, application.getString("application.hypothesis") + phraseHyp);
//    System.out.println(application.getString("application.hypothesis") + phraseHyp);

    String[] phrase1Tokenized = phraseRef.split(" ");
    String[] phrase2Tokenized = phraseHyp.split(" ");


    /* Creating the DTW distance matrix */
    int[][] DTWDistance = new int[phrase1Tokenized.length + 1][phrase2Tokenized.length + 1];
    char[][] DTWTrace = new char[phrase1Tokenized.length + 1][phrase2Tokenized.length + 1];
    for (int i = 0; i <= phrase1Tokenized.length; i++) {
      for (int j = 0; j <= phrase2Tokenized.length; j++) {
        DTWDistance[i][j] = Integer.MAX_VALUE - 400; // hack with allows the penalisation of insertion and deletion
        DTWTrace[i][j] = ' ';
      }
    }
    DTWDistance[0][0] = 0;
    DTWTrace[0][0] = '\\';

    for (int i = 1; i <= phrase1Tokenized.length; i++) {
      for (int j = 1; j <= phrase2Tokenized.length; j++) {
        //int cost = Math.abs(phrase2Transformed[j] - phrase1Transformed[i]);
        int cost = (phrase1Tokenized[i - 1].equals(phrase2Tokenized[j - 1])) ? 0 : 2;

        //int tmp = (DTWDistance[i][j - 1])+4;
        //System.out.println("tmp "+tmp + " DTW "+DTWDistance[i][j - 1]);
        int substitution_cost = DTWDistance[i - 1][j - 1] + 1;
        int deletion_cost = DTWDistance[i - 1][j] + 3;
        int insertion_cost = DTWDistance[i][j - 1] + 3;
        int min = Math.min(deletion_cost, Math.min(insertion_cost, substitution_cost));

        // System.out.println("i " + i + " " + phrase1Tokenized[i - 1] + " j " + j + " " + phrase2Tokenized[j - 1] + " cost " + cost + " min " + min);
        DTWDistance[i][j] = cost + min;
        if (substitution_cost == min) {
          DTWTrace[i][j] = '\\';
        } else if (insertion_cost == min) {
          DTWTrace[i][j] = '-';
        } else if (deletion_cost == min) {
          DTWTrace[i][j] = '|';
        }
      }
    }

    /* Initialising DTW trace matrix and analysis matrix */
    int[][] DTWMatches = new int[DTWDistance.length][DTWDistance[0].length];
    DTWMatchesChar = new char[DTWDistance.length][DTWDistance[0].length];
    for (int i = 0; i < DTWDistance.length; i++) {
      for (int j = 0; j < DTWDistance[0].length; j++) {
        DTWMatches[i][j] = 0;
        DTWMatchesChar[i][j] = '-';
      }
    }

    /* Creating DTW trace matrix and analysing it */
    int currI = DTWDistance.length - 1;
    int currJ = DTWDistance[0].length - 1;
    DTWMatches[currI][currJ] = 1; // Last word is always well aligned
    DTWMatchesChar[currI][currJ] = 's';
    while (currI >= 0 && currJ >= 0) {
      switch (DTWTrace[currI][currJ]) {
        case '-': { // insertion here
          DTWMatches[currI][currJ] = 1;
          DTWMatchesChar[currI][currJ] = 'i';
          currJ--;
          break;
        }
        case '|': { // deletion here
          DTWMatches[currI][currJ] = 1;
          DTWMatchesChar[currI][currJ] = 'd';
          currI--;
          break;
        }
        case '\\': { // match or substitution here
          DTWMatches[currI][currJ] = 1;
          DTWMatchesChar[currI][currJ] = 's';
          currI--;
          currJ--;
          break;
        }
        default: { // exit here
          currI = 0;
          currJ = 0;
          break;
        }
      }
    }

    /* Output the results (including intermediate result) matrixes */
    /* We only need this information for debug purposes */
    /* DTW matrix */
//    System.out.println("Distance"); 
//    System.out.println(application.getString("application.distance"));
    StringBuffer sBuf = new StringBuffer();
    sBuf.append(application.getString("application.distance")+"\n");
//    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, application.getString("application.distance"));
    
    for (int i = 0; i < DTWDistance.length; i++) {
      for (int j = 0; j < DTWDistance[0].length; j++) {
          sBuf.append(DTWDistance[i][j]+" \t");
//          Logger.getLogger(DTWController.class.getName()).log(Level.INFO, DTWDistance[i][j]+" \t");
//        System.out.print(DTWDistance[i][j] + " \t");
      }
      sBuf.append("\n");
//      Logger.getLogger(DTWController.class.getName()).log(Level.INFO, "\n");
//      System.out.println();
    }
    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, sBuf.toString());
    /* DTW trace */
//    System.out.println("Trace");
//    System.out.println(application.getString("application.trace"));
    sBuf=new StringBuffer();
    sBuf.append(application.getString("application.trace")+"\n");
//    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, application.getString("application.trace"));
    for (int i = 0; i < DTWTrace.length; i++) {
      for (int j = 0; j < DTWTrace[0].length; j++) {
//        System.out.print(DTWTrace[i][j]);
          sBuf.append(DTWTrace[i][j]);
//        Logger.getLogger(DTWController.class.getName()).log(Level.INFO, ""+DTWTrace[i][j]);
      }
      sBuf.append("\n");
//      Logger.getLogger(DTWController.class.getName()).log(Level.INFO, "\n");
//      System.out.println();
    }
    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, sBuf.toString());
    /* DTW trace (0-1 line) */
//    System.out.println("Matches");
//    System.out.println(application.getString("application.matches"));
    sBuf=new StringBuffer();
    sBuf.append(application.getString("application.matches")+"\n");
//    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, application.getString("application.matches"));
    for (int i = 0; i < DTWMatches.length; i++) {
      for (int j = 0; j < DTWMatches[0].length; j++) {
          sBuf.append(DTWTrace[i][j]+" ");
//          Logger.getLogger(DTWController.class.getName()).log(Level.INFO, DTWTrace[i][j]+" ");
//        System.out.print(DTWMatches[i][j] + " ");
      }
      sBuf.append("\n");
//      Logger.getLogger(DTWController.class.getName()).log(Level.INFO, "\n");
//      System.out.println();
    }
   Logger.getLogger(DTWController.class.getName()).log(Level.INFO, sBuf.toString());
    /* DTW error analysis */
//    System.out.println("Error");
//    System.out.println(application.getString("application.error"));
    sBuf=new StringBuffer();
    sBuf.append(application.getString("application.error")+"\n");
//    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, application.getString("application.error"));
    for (int i = 1; i < DTWMatchesChar.length; i++) {
      for (int j = 1; j < DTWMatchesChar[0].length; j++) {
          sBuf.append(DTWMatchesChar[i][j] + " ");
//          Logger.getLogger(DTWController.class.getName()).log(Level.INFO, DTWMatchesChar[i][j] + " ");
//        System.out.print(DTWMatchesChar[i][j] + " ");
      }
      sBuf.append("\n");
//      Logger.getLogger(DTWController.class.getName()).log(Level.INFO, "\n");
//      System.out.println();
    }
    Logger.getLogger(DTWController.class.getName()).log(Level.INFO, sBuf.toString());
    /* Creating the errors list */
    for (int i = 1; i < DTWMatchesChar.length; i++) {
      for (int j = 1; j < DTWMatchesChar[0].length; j++) {
        switch (DTWMatchesChar[i][j]) {
          case 's':
            match(phrase1Tokenized[i - 1], phrase2Tokenized[j - 1], i - 1, j - 1);
            break;
          case 'd':
            deletion(phrase1Tokenized[i - 1], i - 1);
            break;
          case 'i':
            insertion(phrase2Tokenized[j - 1], j - 1);
            break;
        }
      }
    }
  }
  private ArrayList<Error> errors = new ArrayList<Error>();
  private char[][] DTWMatchesChar;
  private int segInd;
  //AH 21/02/2013 add internationalization support, and Logger instead of syso
  private ResourceBundle application;
  
}
