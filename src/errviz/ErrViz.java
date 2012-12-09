package errviz;

import controller.STM;
import model.Sector;
import model.Word;
import view.MainForm;

public class ErrViz {

    public static void main(String[] args) {
        /* Just test data set */
        /*
        Sector s = new Sector();
        s.AddWord(new Word("I"));
        s.AddWord(new Word("live"));
        s.AddWord(new Word("here"));
        s.AddWord(new Word("for"));
        s.AddWord(new Word("a"));
        s.AddWord(new Word("long"));
        s.AddWord(new Word("time"));
        Sector s2 = new Sector();
        s2.AddWord(new Word("I"));
        s2.AddWord(new Word("love"));
        s2.AddWord(new Word("ice"));
        s2.AddWord(new Word("cream"));
        
        STM.getInstance().AddSector(s);
        STM.getInstance().AddSector(s2);
        */      

        MainForm MainFrame = new MainForm();
        //MainFrame.updateSTM();
        MainFrame.setVisible(true);
    }
}
