package me.mfransen.openmonsters.launcher.gui;

import me.mfransen.openmonsters.launcher.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

public class ProgressDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    public JProgressBar progressBar1;
    private JLabel statusLabel;

    public ProgressDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        dispose();
    }
    public static void showDialog(final InputStream stream, final File output, final int size, final File outputFolder) {
        final ProgressDialog dialog = new ProgressDialog();
        dialog.pack();
        SwingWorker<Integer,Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                int bytesRead = -1;
                long totalBytes = 0;
                byte[] buffer = new byte[4096];
                FileOutputStream os = new FileOutputStream(output);
                while((bytesRead=stream.read(buffer))!=-1) {
                    os.write(buffer,0,bytesRead);
                    totalBytes += bytesRead;
                    setProgress((int)((totalBytes*100)/size));
                    Main.logger.info((totalBytes*100)/size+"%");
                }
                os.close();
                dialog.statusLabel.setText("Extracting...");
                dialog.progressBar1.setIndeterminate(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            outputFolder.delete();
                            outputFolder.mkdir();
                            Main.sal.extractAddon(output,outputFolder);
                            output.delete();
                            dialog.progressBar1.setVisible(false);
                            dialog.statusLabel.setText("Update Complete.");
                            dialog.buttonOK.setVisible(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return 0;
            }
        };
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if("progress".equalsIgnoreCase(evt.getPropertyName())) {
                    dialog.progressBar1.setValue((Integer)evt.getNewValue());
                }
            }
        });
        worker.execute();
        dialog.setVisible(true);
    }
}
