package me.mfransen.openmonsters.dev.gui;

import me.mfransen.openmonsters.dev.Asset;
import me.mfransen.openmonsters.dev.Project;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by matt on 12/12/15.
 */
public class MainForm {
    public JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTree tree1;
    public Project project;
    public MainForm() {
        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getClickCount()==2) {
                    int row = tree1.getRowForLocation(e.getX(),e.getY());
                    if(row!=-1) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getPathForRow(row).getLastPathComponent();
                        Object o = node.getUserObject();
                        if(o instanceof Asset) {
                            Asset a = (Asset)o;
                            File f = a.getFile();
                            if(f.getName().endsWith(".png")||f.getName().endsWith(".jpg")) {
                                addTab(a.getName(),new TextureForm(f).mainPanel);
                            }
                        }
                    }
                }
            }
        });
    }
    public void loadProject(File project) {
        try {
            loadProject(new Project(project));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    private void loadProject(Project project) {
        tree1.setModel(this.project = project);
    }
    public void addTab(String title, Component body) {
        tabbedPane1.addTab(title,body);
        final int i = tabbedPane1.getTabCount()-1;
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        JLabel tabTitle = new JLabel(title+"    ");
        JLabel btnClose = new JLabel("x");
        btnClose.setEnabled(false);
        btnClose.setFont(btnClose.getFont().deriveFont(Collections.singletonMap(TextAttribute.WEIGHT,TextAttribute.WEIGHT_ULTRABOLD)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=1;
        panel.add(tabTitle,gbc);
        gbc.gridx++;
        gbc.weightx=0;
        panel.add(btnClose,gbc);
        tabbedPane1.setTabComponentAt(i,panel);
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tabbedPane1.remove(tabbedPane1.indexOfTabComponent(panel));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JLabel)e.getSource()).setEnabled(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JLabel)e.getSource()).setEnabled(false);
            }
        });
    }
}
