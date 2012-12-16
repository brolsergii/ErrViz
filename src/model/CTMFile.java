package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author axel
 * @author sergii
 */
public class CTMFile {
    
    private String fileName;
    private ArrayList<Sector> aSectorArray;
    private ArrayList<Word> aWordArray;
    
    public CTMFile(String s){
        fileName = s;
        aWordArray = new ArrayList<Word>();
        aSectorArray = new ArrayList<Sector>();
    }
    
    public void doSegmentation(STMFile stmfile)
    {
        for (Sector seg1 : stmfile.getaSectorArray()){
            float begin = seg1.getBegin();
            float end = seg1.getEnd();
            String CTMSentence = "";
            for (Word wrd : aWordArray)
            {
                float timeWrd = (wrd.getBegin() + wrd.getEnd())/2;
                if ( timeWrd > begin && timeWrd < end )
                    CTMSentence += wrd.getWord() + " ";
            }
            CTMSentence = CTMSentence.trim();
            Sector seg2 = new Sector(CTMSentence, begin, end, seg1.getaSpeaker(),
                   seg1.getConditions(), seg1.getSentence().get(0).getIndex());
            aSectorArray.add(seg2);
        }
    }

    public ArrayList<Word> getaWordArray() {
        return aWordArray;
    }
    
    public ArrayList<Sector> getSectorArray() {
        return aSectorArray;
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
