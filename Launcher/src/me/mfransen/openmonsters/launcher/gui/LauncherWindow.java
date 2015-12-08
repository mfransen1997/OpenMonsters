package me.mfransen.openmonsters.launcher.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Created by matt on 12/8/15.
 */
public class LauncherWindow extends JFrame {
    LauncherForm form = new LauncherForm();
    public LauncherWindow() {
        super("OpenMonsters");
        setContentPane(form.mainPanel);
        Logger.getLogger("OpenMonsters").addHandler(new TextPaneLogger(form.consoleTextPane));
        setPreferredSize(new Dimension(800,600));
        form.addonTable.setModel(new DefaultTableModel(new String[]{"name","author","version"},0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        pack();
        setPreferredSize(new Dimension(800,600));
    }
}
