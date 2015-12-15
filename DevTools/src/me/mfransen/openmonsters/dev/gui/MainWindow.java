package me.mfransen.openmonsters.dev.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainForm mainForm = new MainForm();
    public MainWindow() {
        super("OpenMonsters Dev Tools");
        setContentPane(mainForm.mainPanel);
        setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(createJMenuBar());
        pack();
    }
    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //file
        JMenu file = new JMenu("File");
        JMenuItem export = new JMenuItem("Export");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.this.dispose();
            }
        });
        file.add(open);
        file.add(save);
        file.add(export);
        file.add(quit);
        menuBar.add(file);
        return menuBar;
    }
    private JMenu createProjectViewContextMenu() {
        return null;
    }
}
