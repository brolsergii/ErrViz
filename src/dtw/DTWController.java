package dtw;

import java.util.ArrayList;

/**
 * @author sergii
 */
public class DTWController {

  public DTWController(int segID) {
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

    System.out.println("REF: " + phraseRef);
    System.out.println("HYP: " + phraseHyp);

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
    System.out.println("Distance");
    for (int i = 0; i < DTWDistance.length; i++) {
      for (int j = 0; j < DTWDistance[0].length; j++) {
        System.out.print(DTWDistance[i][j] + " \t");
      }
      System.out.println();
    }
    /* DTW trace */
    System.out.println("Trace");
    for (int i = 0; i < DTWTrace.length; i++) {
      for (int j = 0; j < DTWTrace[0].length; j++) {
        System.out.print(DTWTrace[i][j]);
      }
      System.out.println();
    }
    /* DTW trace (0-1 line) */
    System.out.println("Matches");
    for (int i = 0; i < DTWMatches.length; i++) {
      for (int j = 0; j < DTWMatches[0].length; j++) {
        System.out.print(DTWMatches[i][j] + " ");
      }
      System.out.println();
    }
    /* DTW error analysis */
    System.out.println("Error");
    for (int i = 1; i < DTWMatchesChar.length; i++) {
      for (int j = 1; j < DTWMatchesChar[0].length; j++) {
        System.out.print(DTWMatchesChar[i][j] + " ");
      }
      System.out.println();
    }
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
}
