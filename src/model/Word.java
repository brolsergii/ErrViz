package model;

import java.util.ResourceBundle;
import utils.PropertiesHelper;

public class Word {

    private String word;
    private float begin;
    private float end;
    private int index;
    private int position;
    private int ID;
    private ResourceBundle application = PropertiesHelper.getApplication();

    public Word(String s, int i) {
        word = s;
        index = i;
        begin = 0;
        end = 0;
        this.ID = getUID();
    }

    public Word(String s, float b, float e) {
        word = s;
        begin = b;
        end = e;
        index = 0;
        this.ID = getUID();
    }

    public int getIndex() {
        return index;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public float getBegin() {
        return begin;
    }

    public void setBegin(float begin) {
        this.begin = begin;
    }

    public float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public int getID() {
        return ID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLength() {
        return word.length();
    }

    @Override
    public String toString() {
//        return "Word : " + word + ", start : " + begin + ", end : " + end + ", index : " + index;
        return application.getString("application.word")+": " + word + ", "
                +application.getString("application.start")+" : " + begin + ", "
                +application.getString("application.end")+": " + end + ", "
                +application.getString("application.index")+": " + index;
    }
    private static int UID = 0;

    private static int getUID() {
        UID++;
        return UID;
    }
}
