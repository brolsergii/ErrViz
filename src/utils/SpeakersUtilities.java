package utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import model.Sector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author axel
 */
public class SpeakersUtilities {
    
    public static String[] getSpeakers(ArrayList<Sector> aSectorArray){
        Iterator<Sector> it = aSectorArray.iterator();
        HashMap aMap = new HashMap();
        String name = "";
        while(it.hasNext()){
            name = it.next().getaSpeaker().toString();
            aMap.put(name,name);
        }
        ArrayList<String> aList = new ArrayList<String>(aMap.values());
        Collections.sort(aList);
        String[] speakers = new String[aList.size()];
        speakers = aList.toArray(speakers);
//        System.out.println(speakers.toString());
//        for(int i=0; i<speakers.length;i++){
//            System.out.println(i+" : "+speakers[i]);
//        }
        return speakers;
    }
    
    public static ArrayList<Sector> getSentencesForASpeaker(ArrayList<Sector> aSectorArray,String name) {
        Iterator<Sector> it = aSectorArray.iterator();
        ArrayList<Sector> aList= new ArrayList<Sector>();
        Sector s;
        
        while(it.hasNext()){
            s=it.next();
            if(s.getaSpeaker().toString().compareTo(name)==0){
                aList.add(s);
            }
        }
        
        return aList;
    }
}
