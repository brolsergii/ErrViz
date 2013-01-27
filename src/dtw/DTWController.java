package dtw;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author sergii
 */
public class DTWController {

  private ArrayList<Error> errors = new ArrayList<Error>();
  private char[][] DTWMatchesChar;

  private void insertion(String str, int number) {
    errors.add(new Insertion(str, number));
  }

  private void deletion(String str, int number) {
    errors.add(new Deletion(str, number));
  }

  private void match(String ref, String hyp, int i, int j) {
    if (!ref.equals(hyp)) {
      errors.add(new Substitution(ref, hyp, i, j));
    }
  }

  public ArrayList<Error> getErrors() {
    return errors;
  }

  public char[][] getAnalisysMatrix() {
    return DTWMatchesChar;
  }

  public void DTWCompare(String phraseRef, String phraseHyp) {

    System.out.println("REF: " + phraseRef);
    System.out.println("HYP: " + phraseHyp);

    /* Creating a vocabulary of words */
    HashMap<String, Integer> vocabulary = new HashMap<String, Integer>();
    int counter = 1;

    String[] phrase1Tokenized = phraseRef.split(" ");
    String[] phrase2Tokenized = phraseHyp.split(" ");

    /* Trasforming pharases into int array */
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

    /* Creating the DTW distance matrix */
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
        DTWDistance[i][j] = cost + min;
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
    int currI = 0;
    int currJ = 0;
    DTWMatches[0][0] = 1;
    DTWMatchesChar[0][0] = 's';
    while (currI < DTWDistance.length - 1 && currJ < DTWDistance[0].length - 1) {
      if (DTWDistance[currI + 1][currJ + 1] <= DTWDistance[currI + 1][currJ]
              && DTWDistance[currI + 1][currJ + 1] <= DTWDistance[currI][currJ + 1]) {
        currI = currI + 1;
        currJ = currJ + 1;
        DTWMatches[currI][currJ] = 1;
        DTWMatchesChar[currI][currJ] = 's';
      } else if (DTWDistance[currI][currJ + 1] < DTWDistance[currI + 1][currJ + 1]
              && DTWDistance[currI][currJ + 1] <= DTWDistance[currI + 1][currJ]) {
        currJ = currJ + 1;
        DTWMatches[currI][currJ] = 1;
        DTWMatchesChar[currI][currJ] = 'i';
      } else if (DTWDistance[currI + 1][currJ] < DTWDistance[currI + 1][currJ + 1]
              && DTWDistance[currI + 1][currJ] <= DTWDistance[currI][currJ + 1]) {
        currI = currI + 1;
        DTWMatches[currI][currJ] = 1;
        DTWMatchesChar[currI][currJ] = 'd';
      }
      if (currJ == DTWDistance[0].length - 1 && currI != DTWDistance.length - 1) {
        for (int i = currI; i < DTWDistance.length; i++) {
          if (DTWMatchesChar[i][currJ] == '-') {
            DTWMatches[i][currJ] = 1;
            DTWMatchesChar[i][currJ] = 'd';
          }
        }
      }
      if (currI == DTWDistance.length - 1 && currJ != DTWDistance[0].length - 1) {
        for (int j = currJ; j < DTWDistance[0].length; j++) {
          if (DTWMatchesChar[currI][j] == '-') {
            DTWMatches[currI][j] = 1;
            DTWMatchesChar[currI][j] = 'i';
          }
        }
      }
    }

    /* Output the result matrixes */
    /* DTW matrix */
    System.out.println("Distance");
    for (int i = 0; i < DTWDistance.length; i++) {
      for (int j = 0; j < DTWDistance[0].length; j++) {
        System.out.print(DTWDistance[i][j] + " ");
      }
      System.out.println();
    }
    /* DTW trace */
    System.out.println("Matches");
    for (int i = 0; i < DTWMatches.length; i++) {
      for (int j = 0; j < DTWMatches[0].length; j++) {
        System.out.print(DTWMatches[i][j] + " ");
      }
      System.out.println();
    }
    /* DTW error analysis */
    System.out.println("Error");
    for (int i = 0; i < DTWMatchesChar.length; i++) {
      for (int j = 0; j < DTWMatchesChar[0].length; j++) {
        System.out.print(DTWMatchesChar[i][j] + " ");
      }
      System.out.println();
    }

    /* Creating the errors list */
    for (int i = 0; i < DTWMatchesChar.length; i++) {
      for (int j = 0; j < DTWMatchesChar[0].length; j++) {
        switch (DTWMatchesChar[i][j]) {
          case 's':
            match(phrase1Tokenized[i], phrase2Tokenized[j], i, j);
            break;
          case 'd':
            deletion(phrase1Tokenized[i], i);
            break;
          case 'i':
            insertion(phrase2Tokenized[j], j);
            break;
        }
      }
    }
  }
}
