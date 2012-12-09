package parserSTM;

import model.Speaker;
import model.Sector;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import model.STMFile;
import view.OutputHelper;

/**
 * @author axel
 */
public class ParserSTM implements Runnable {

    private STMFile file;

    public ParserSTM(String s) {
        file = new STMFile(s);
    }

    public ParserSTM(STMFile aFile) {
        file = aFile;
    }

    public STMFile getFile() {
        return file;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file.getFileName())));
            String tempEnco = input.readLine();
            String[] tabEnco = tempEnco.split(" ");
            //String encoding = new String(tabEnco[tabEnco.length-1]);
            StringBuilder encoding = new StringBuilder();
            encoding.append(tabEnco[tabEnco.length - 1]);
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
            //OutputHelper.debugOut("encodage : " + encoding.toString() + "\n");
            //BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file),"8859_1"));
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file.getFileName()), encoding.toString()));
            String line;
            float begin = 0;
            float end = 0;
            StringBuilder locutorName = new StringBuilder();
            StringBuilder conditions = new StringBuilder();
            StringBuilder sentence = new StringBuilder();
            StringBuilder sex = new StringBuilder();
            int index = 0;
            do {
                line = input.readLine();
                if (line != null) {
                    if (line.charAt(0) != ';') {
                        String[] elements = line.split(" ");
                        locutorName.append(elements[2]);
                        begin = Float.parseFloat(elements[3]);
                        end = Float.parseFloat(elements[4]);
                        String temp = elements[5];
                        String[] temp2 = temp.split(",");
                        conditions.append(temp2[1]);
                        sex.append(temp2[2].replace(">", ""));
                        int i = 6;
                        //StringBuilder aSBuf =new StringBuffer("");
                        for (i = 6; i < elements.length; i++) {
                            index++;
                            sentence.append(elements[i]);
                            sentence.append(" ");
                        }
                        //sentence = aSBuf.toString();


                        Sector s = new Sector(sentence.toString(), begin, end, new Speaker(locutorName.toString(), (sex.toString().compareTo("male") == 0 ? 0 : 1)), conditions.toString(), file.getOffset());
                        sentence.setLength(0);
                        locutorName.setLength(0);
                        sex.setLength(0);
                        conditions.setLength(0);
                        //OutputHelper.debugOut(s.toString());
                        file.addSector(s);
                    }
                }
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
