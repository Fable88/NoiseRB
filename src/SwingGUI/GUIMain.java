package SwingGUI;

import Backend.FileOperations;

import javax.swing.*;
import java.io.IOException;

public class GUIMain {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Form form = new Form();
    }
}
