package me.mfransen.openmonsters.launcher;

import me.mfransen.openmonsters.launcher.gui.LauncherWindow;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;


public class Main {
    public static Logger logger = Logger.getLogger("OpenMonsters");
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        LauncherWindow window = new LauncherWindow();
        window.setVisible(true);
        logger.info("Setting data folder: "+getDataFolder(System.getProperty("os.name")));
    }
    private static String getDataFolder(String os) {
        if(os.toLowerCase().contains("windows")) {
            return System.getenv("APPDATA")+ File.separator+"OpenMonsters";
        } else if(os.toLowerCase().contains("mac")) {
            return System.getProperty("user.home")+File.separator+"Application Support"+File.separator+"OpenMonsters";
        } else {
            return System.getProperty("user.home")+File.separator+"OpenMonsters";
        }
    }
}
