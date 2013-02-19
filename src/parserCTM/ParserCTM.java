package parserCTM;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import model.CTMFile;
import model.Word;

/**
 * @author axel
 */
public class ParserCTM implements Runnable {

  CTMFile file;

  public ParserCTM(String s) {
    file = new CTMFile(s);
  }

  public ParserCTM(CTMFile aFile) {
    file = aFile;
  }

  public CTMFile getFile() {
    return file;
  }


  @Override
  public void run() {
    try {
      
      StringBuilder encoding = null;
      BufferedReader input = null;
      try {
        String stmName = file.getFileName().replaceAll("MAJ.ctm", "stm");
        stmName = stmName.replaceAll(".ctm", ".stm");
        //System.out.println("stmName = " + stmName);
        input = new BufferedReader(new InputStreamReader(new FileInputStream(stmName.toString())));
        String tempEnco = input.readLine();
        String[] tabEnco = tempEnco.split(" ");
        encoding = new StringBuilder(tabEnco[tabEnco.length - 1]);
      } catch (Exception ex) {
        encoding = new StringBuilder("UTF8");
      }
      if (encoding.toString().compareTo("ISO-8859-1") == 0) {
        encoding.setLength(0);
        encoding.append("8859_1");
      }
      if (encoding.toString().compareTo("UTF-8") == 0) {
        encoding.setLength(0);
        encoding.append("UTF8");
      }
      if (encoding.toString().compareTo("US-ASCII") == 0) {
        encoding.setLength(0);
        encoding.append("ASCII");
      }
      //OutputHelper.debugOut("encodage : "+encoding.toString()+"\n");
      input = new BufferedReader(new InputStreamReader(new FileInputStream(file.getFileName()), encoding.toString()));
      String line;
      float begin = 0;
      float end = 0;
      StringBuilder word = new StringBuilder();
      Word aWord;
      do {
        line = input.readLine();
        if (line != null) {
          String[] elements = line.split(" ");
          if (!elements[4].contains("<") && !elements[4].contains(">")
                  && !elements[4].contains("[") && !elements[4].contains("]")) {
            word.append(elements[4]);
            begin = Float.parseFloat(elements[2]);
            end = begin + Float.parseFloat(elements[3]);
            aWord = new Word(word.toString(), begin, end);
            file.addWord(aWord);
            word.setLength(0);
            //OutputHelper.debugOut(aWord.toString());
          }
        }
      } while (line != null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
