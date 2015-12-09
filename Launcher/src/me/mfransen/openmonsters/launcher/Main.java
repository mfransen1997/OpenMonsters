package me.mfransen.openmonsters.launcher;

import me.mfransen.openmonsters.launcher.gui.LauncherWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class Main {
    public static Logger logger = Logger.getLogger("OpenMonsters");
    public static File dataFolder;
    public static File pluginFolder;
    public static SimpleAddonLoader sal = new SimpleAddonLoader();
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, JAXBException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        LauncherWindow window = new LauncherWindow();
        window.setVisible(true);
        dataFolder = new File(getDataFolder(System.getProperty("os.name")));
        if(!dataFolder.exists())
            dataFolder.mkdir();
        logger.info("Setting data folder: "+dataFolder.getPath());
        if(!(pluginFolder = new File(dataFolder,"Addons")).exists())
            pluginFolder.mkdir();
        sal.loadAddons(pluginFolder);
        for(AddonInfo info : sal.addons)
            ((DefaultTableModel)window.form.addonTable.getModel()).addRow(new Object[]{info.getId(),info.getAuthor(),info.getVersion()});

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
