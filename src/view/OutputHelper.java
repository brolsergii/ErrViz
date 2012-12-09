package view;

import javax.swing.JOptionPane;

public class OutputHelper {

    private static boolean _DEBUG = true;

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
        Object[] options = {"Yes", "No"};
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
