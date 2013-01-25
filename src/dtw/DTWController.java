package dtw;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author sergii
 */
public class DTWController {

  public ArrayList<Error> errors = new ArrayList<Error>();

  private void insertion(String str, int number) {
    errors.add(new Insertion(str, number));
  }

  private void deletion(String str, int number) {
    errors.add(new Deletion(str, number));
  }

  private void math(String ref, String hyp, int i, int j) {
    if (!ref.equals(hyp)) {
      errors.add(new Substitution(ref, hyp, i, j));
    }
  }

  public void DTWCompare(String phraseRef, String phraseHyp) {

    /* Creating a vocabulary of words */
    HashMap<String, Integer> vocabulary = new HashMap<String, Integer>();
    int counter = 1;

    String[] phrase1Tokenized = phraseRef.split(" ");
    String[] phrase2Tokenized = phraseHyp.split(" ");

    /* Trasforming pharases */
    int[] phrase1Transformed = new int[phrase1Tokenized.length];
    int[] phrase2Transformed = new int[phrase2Tokenized.length];

    int phraseWordCounter = 0;
    for (String word : phrase1Tokenized) {
      word = word.trim();
      if (!vocabulary.containsKey(word)) {
        vocabulary.put(word, counter);
        counter += 1;
      }
      phrase1Transformed[phraseWordCounter] = vocabulary.get(word);
      phraseWordCounter++;
    }
    phraseWordCounter = 0;
    for (String word : phrase2Tokenized) {
      word = word.trim();
      if (!vocabulary.containsKey(word)) {
        vocabulary.put(word, counter);
        counter += 1;
      }
      phrase2Transformed[phraseWordCounter] = vocabulary.get(word);
      phraseWordCounter++;
    }

    // Creating a distance matrix
    int[][] DTWDistance = new int[phrase1Transformed.length][phrase2Transformed.length];
    for (int i = 0; i < phrase1Transformed.length; i++) {
      for (int j = 0; j < phrase2Transformed.length; j++) {
        DTWDistance[i][j] = Integer.MAX_VALUE;
      }
    }
    DTWDistance[0][0] = 0;

    for (int i = 1; i < phrase1Transformed.length; i++) {
      for (int j = 1; j < phrase2Transformed.length; j++) {
        //int cost = Math.abs(phrase2Transformed[j] - phrase1Transformed[i]);
        int cost = (phrase1Transformed[i] == phrase2Transformed[j]) ? 0 : 1;
        int min = Math.min(DTWDistance[i - 1][j],
                Math.min(DTWDistance[i][j - 1],
                DTWDistance[i - 1][j - 1]));
//        if (min == DTWDistance[i - 1][j]) {
//          System.out.println("insertion (" + i + "," + j + ") {" + phrase1Tokenized[i] + ", " + phrase2Tokenized[j] + "}");
//        }
//        if (min == DTWDistance[i][j - 1]) {
//          System.out.println("deletion (" + i + "," + j + ") {" + phrase1Tokenized[i] + ", " + phrase2Tokenized[j] + "}");
//        }
//        if (min == DTWDistance[i][j]) {
//          System.out.println("match (" + i + "," + j + ") {" + phrase1Tokenized[i] + ", " + phrase2Tokenized[j] + "}");
//        }
        DTWDistance[i][j] = cost + min;
      }
    }

    /* Searching for matches */
    int[][] DTWMatchesTmp = new int[DTWDistance.length][DTWDistance[0].length];
    for (int j = 0; j < DTWDistance[0].length; j++) {
      int minCol = Integer.MAX_VALUE;
      for (int i = 0; i < DTWDistance.length; i++) {
        if (DTWDistance[i][j] < minCol) {
          minCol = DTWDistance[i][j];
        }
      }
      for (int i = 0; i < DTWDistance.length; i++) {
        if (DTWDistance[i][j] != minCol) {
          DTWMatchesTmp[i][j] = 0;
        } else {
          DTWMatchesTmp[i][j] = 1;
        }
      }
    }
    int[][] DTWMatches = new int[DTWDistance.length][DTWDistance[0].length];
    for (int i = 0; i < DTWDistance.length; i++) {
      int minLine = Integer.MAX_VALUE;
      int minLineJ = -1;
      for (int j = 0; j < DTWDistance[0].length; j++) {
        if (DTWDistance[i][j] < minLine) {
          minLine = DTWDistance[i][j];
          minLineJ = j;
        }
        System.out.print(DTWDistance[i][j] + "  \t");
      }
      System.out.println();
      for (int j = 0; j < DTWDistance[0].length; j++) {
        //if (DTWDistance[i][j] != minLine) {
        if (j != minLineJ) {
          DTWMatches[i][j] = 0;
        } else {
          DTWMatches[i][j] = 1;
        }
      }
    }

    /* Output the tmp matching matrix */
    //System.out.println();
    //for (int i = 0; i < DTWMatchesTmp.length; i++) {
    //  for (int j = 0; j < DTWMatchesTmp[0].length; j++) {
    //    System.out.print(DTWMatchesTmp[i][j] + " ");
    //  }
    //  System.out.println();
    //}

    /* Output the matching matrix */
    System.out.println();
    for (int i = 0; i < DTWMatches.length; i++) {
      for (int j = 0; j < DTWMatches[0].length; j++) {
        System.out.print(DTWMatches[i][j] + " ");
      }
      System.out.println();
    }

    for (int i = 0; i < DTWMatches.length; i++) {
      for (int j = 0; j < DTWMatches[0].length; j++) {
        // A hack to avoid the precessing of the whole matrix and get empty collumns
        if (DTWMatches[i][j] == 1 || i == DTWMatches.length - 1) {
          int sumCollumn = 0;
          int sumRow = 0;

          for (int m = 0; m < DTWMatches.length; m++) {
            sumCollumn += DTWMatches[m][j];
          }
          for (int m = 0; m < DTWMatches[0].length; m++) {
            sumRow += DTWMatches[i][m];
          }
          // Match case
          if (sumCollumn == 1 && sumRow == 1 && DTWMatches[i][j] == 1) {
            System.out.println("Match {" + phrase1Tokenized[i] + "; " + phrase2Tokenized[j] + "}");
            math(phrase1Tokenized[i], phrase2Tokenized[j], i, j);
          } // Deletion or match
          else if (sumCollumn > 1 && sumRow == 1 && DTWMatches[i][j] == 1) {
            int firstI = 0;
            for (int m = 0; m < DTWMatches.length; m++) {
              if (DTWMatches[m][j] == 1) {
                firstI = m;
                break;
              }
            }
            if (i == firstI) {
              System.out.println("Match {" + phrase1Tokenized[i] + "; " + phrase2Tokenized[j] + "}");
              math(phrase1Tokenized[i], phrase2Tokenized[j], i, j);
            } else {
              System.out.println("Deletion {" + phrase1Tokenized[i] + "}");
              deletion(phrase1Tokenized[i], i);
            }
          } // Insertion ot match
          else if (sumRow > 1 && sumCollumn == 1 && DTWMatches[i][j] == 1) {
            int firstJ = 0;
            for (int m = 0; m < DTWMatches[0].length; m++) {
              if (DTWMatches[i][j] == 1) {
                firstJ = m;
                break;
              }
            }
            if (j == firstJ) {
              System.out.println("Match {" + phrase1Tokenized[i] + "; " + phrase2Tokenized[j] + "}");
              math(phrase1Tokenized[i], phrase2Tokenized[j], i, j);
            } else {
              System.out.println("Insertion {" + phrase2Tokenized[j] + "}");
              insertion(phrase2Tokenized[j], j);
            }
          } // Insertion
          else if (sumCollumn == 0) {
            System.out.println("Insertion {" + phrase2Tokenized[j] + "}");
            insertion(phrase2Tokenized[j], j);
          }
        }
      }
    }
  }
}
