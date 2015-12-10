package me.mfransen.openmonsters.launcher;

import com.bulenkov.darcula.DarculaLaf;
import me.mfransen.openmonsters.launcher.gui.LauncherWindow;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class Main {
    public static Logger logger = Logger.getLogger("OpenMonsters");
    public static File dataFolder;
    public static File addonsFolder;
    public static SimpleAddonLoader sal = new SimpleAddonLoader();
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, JAXBException, ParserConfigurationException, SAXException {
        UIManager.setLookAndFeel(new DarculaLaf());
        LauncherWindow window = new LauncherWindow();
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dataFolder = new File(getDataFolder(System.getProperty("os.name")));
        if(!dataFolder.exists())
            if(!dataFolder.mkdir())
                logger.severe("Unable to create Data folder.");
        logger.info("Setting data folder: "+dataFolder.getPath());
        if(!(addonsFolder = new File(dataFolder,"Addons")).exists())
            if(!addonsFolder.mkdir())
                logger.warning("Unable to create Addons folder.");
        reloadAddonInfo(window.form.addonTable);
        int version = Updater.getVersion();
        int latest = Updater.getLatestVersion();
        if(latest>version)
            Updater.Update(latest);
    }
    public static void reloadAddonInfo(JTable table) throws ParserConfigurationException, JAXBException, SAXException, IOException {
        sal.addons.clear();
        table.removeAll();
        sal.loadAddons(addonsFolder);
        for(AddonInfo info : sal.addons) {
            String vers = info.getVersion();
            Version version = new Version(vers);
            if(info.downloadInfo!=null&&version.isOlder(info.downloadInfo.version))
                vers+=String.format(" - Update Available (%s)",info.downloadInfo.version);
            ((DefaultTableModel) table.getModel()).addRow(new Object[]{info.getId(), info.getAuthor(), vers});
        }
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
