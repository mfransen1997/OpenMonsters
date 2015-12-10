package me.mfransen.openmonsters.launcher.gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by matt on 12/8/15.
 */
public class TextPaneLogger extends Handler {
    JTextPane textPane;
    public TextPaneLogger(JTextPane pane) {
       textPane = pane;
    }
    @Override
    public void publish(LogRecord record) {
        SimpleAttributeSet keyword = new SimpleAttributeSet();
        if(record.getLevel()== Level.WARNING)
            StyleConstants.setForeground(keyword, Color.orange);
        else if(record.getLevel()==Level.SEVERE)
            StyleConstants.setForeground(keyword, Color.red);
        else
            StyleConstants.setForeground(keyword, Color.white);
        //StyleConstants.setBold(keyword,true);
        try {
            textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(),String.format("[%1s] %2s\n",record.getLevel().getLocalizedName(),record.getMessage()),keyword);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
