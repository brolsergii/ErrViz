package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author axel and Babacar
 */
public class STMFile {
    
    private String fileName;
    private ArrayList<Sector> aSectorArray;
    
    public STMFile(String s){
        fileName = s;
        aSectorArray = new ArrayList<Sector>();
    }
    
    public int getOffset(){
        int i=0;
        Iterator<Sector> it = aSectorArray.iterator();
        while(it.hasNext()){
            i+=it.next().getLength();
        }
        return i;
    }

    public ArrayList<Sector> getaSectorArray() {
        return aSectorArray;
    }

    public void setaSectorArray(ArrayList<Sector> aSectorArray) {
        this.aSectorArray = aSectorArray;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Sector getSectorAt(int indexe){
        return aSectorArray.get(indexe);
    }
    
    public void setSectorAt(int indexe, Sector secteur){
        aSectorArray.set(indexe, secteur);
    }
    
    public void addSector(Sector s){
        aSectorArray.add(s);
    }
    
}
