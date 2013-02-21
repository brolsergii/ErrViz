package view;

import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import utils.PropertiesHelper;

public class OutputHelper {

    private static boolean _DEBUG = true;
    private static ResourceBundle application = PropertiesHelper.getApplication();

    public static void debugOut(Object message) {
        if (_DEBUG) {
            System.out.println(message.toString());
        }
    }

    public static void showInfoMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static boolean askQuestion(String question, String title) {
        //AH 21/02/2013 Internationalization
        //        Object[] options = {"Yes", "No"};
        Object[] options = {application.getString("application.yes"), application.getString("application.no")};
        int n = JOptionPane.showOptionDialog(null, question, title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title
        if (n == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
}
