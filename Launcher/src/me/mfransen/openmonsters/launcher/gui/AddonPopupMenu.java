package me.mfransen.openmonsters.launcher.gui;

import me.mfransen.openmonsters.launcher.AddonInfo;
import me.mfransen.openmonsters.launcher.Main;
import me.mfransen.openmonsters.launcher.Version;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by matt on 12/9/15.
 */
public class AddonPopupMenu extends JPopupMenu {
    AddonInfo addon;
    private ActionListener update = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            addon.folder.delete();
            addon.folder.mkdir();
            try {
                advanceDownload(addon.getId(),addon.downloadInfo.download);
                //JOptionPane.showMessageDialog(null,"Download Complete","Download Complete",JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };
    public AddonPopupMenu(AddonInfo addon) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.addon = addon;
        if(addon.downloadInfo!=null&&new Version(addon.getVersion()).isOlder(addon.downloadInfo.version)) {
            add(createItem("Update", update));
        } else if(addon.downloadInfo!=null) {
            add(createItem("Force Update", update));
        }
        add(createItem("Remove", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.logger.info("removed.");
            }
        }));
    }
    private JMenuItem createItem(String label, ActionListener listener) {
        JMenuItem output = new JMenuItem(label);
        output.addActionListener(listener);
        return output;
    }
    private void advanceDownload(String name, String url) throws IOException {
        URL u = new URL(url);
        int size = u.openConnection().getContentLength();
        InputStream is = u.openStream();
        File f = new File(Main.dataFolder,"Cache"+File.separator+name+".zip");
        if(f.exists())
            f.delete();
        ProgressDialog.showDialog(is,f,size,new File(Main.addonsFolder,name));
    }
}
