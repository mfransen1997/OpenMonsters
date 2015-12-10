package me.mfransen.openmonsters.launcher.gui;

import me.mfransen.openmonsters.launcher.AddonInfo;
import me.mfransen.openmonsters.launcher.Launcher;
import me.mfransen.openmonsters.launcher.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
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
        form.addonTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()>=2&&e.getButton()==MouseEvent.BUTTON1) {
                    new AddonPopupMenu(Main.sal.addons.get(form.addonTable.getSelectedRow())).show(e.getComponent(),e.getComponent().getX(),form.addonTable.getRowHeight()*(form.addonTable.getSelectedRow()+1)+e.getComponent().getY());
                }
            }
        });
        form.launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    form.loginPanel.setVisible(false);
                    form.tabbedPane1.removeTabAt(2);
                    form.tabbedPane1.removeTabAt(1);
                    Launcher.launch();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    private void createOptionsNode(DefaultMutableTreeNode rootNode) {
        rootNode.add(new DefaultMutableTreeNode("Simple"));
        rootNode.add(new DefaultMutableTreeNode("Advanced"));
        rootNode.add(new DefaultMutableTreeNode("Developer"));
    }
}
