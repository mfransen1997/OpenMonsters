package me.mfransen.openmonsters.dev;

import com.bulenkov.darcula.DarculaLaf;
import me.mfransen.openmonsters.dev.gui.MainForm;
import me.mfransen.openmonsters.dev.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by matt on 12/12/15.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        if(args.length>0) {
            mainWindow.mainForm.loadProject(new File(args[0]));
        }
    }
}
