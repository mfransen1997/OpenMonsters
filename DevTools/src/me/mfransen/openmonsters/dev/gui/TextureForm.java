package me.mfransen.openmonsters.dev.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by matt on 12/15/15.
 */
public class TextureForm {
    public JPanel mainPanel;
    private JLabel textureLabel;
    public TextureForm(File f) {
        try {
            System.out.println(f.getAbsolutePath());
            ImageIcon ico = new ImageIcon(f.toURI().toURL());
            textureLabel.setIcon(ico);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
