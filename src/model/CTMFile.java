package model;

import java.util.ArrayList;

/**
 * @author axel and Babacar
 */
public class CTMFile {
    
    private String fileName;
    private ArrayList<Word> aWordArray;
    
    public CTMFile(String s){
        fileName = s;
        aWordArray = new ArrayList<Word>();
    }

    public ArrayList<Word> getaWordArray() {
        return aWordArray;
    }

    public void setaWordArray(ArrayList<Word> aWordArray) {
        this.aWordArray = aWordArray;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void addWord(Word w){
        aWordArray.add(w);
    }
    
    
}
