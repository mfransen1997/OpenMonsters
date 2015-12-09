package me.mfransen.openmonsters.launcher.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.util.logging.Logger;

/**
 * Created by matt on 12/8/15.
 */
public class LauncherWindow extends JFrame {
    public LauncherForm form = new LauncherForm();
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Options");
        createOptionsNode(root);
        ((DefaultTreeModel)form.optionsMain.getModel()).setRoot(root);

    }
    private void createOptionsNode(DefaultMutableTreeNode rootNode) {
        rootNode.add(new DefaultMutableTreeNode("Simple"));
        rootNode.add(new DefaultMutableTreeNode("Advanced"));
        rootNode.add(new DefaultMutableTreeNode("Developer"));
    }
}
