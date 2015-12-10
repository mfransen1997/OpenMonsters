package me.mfransen.openmonsters.launcher;

import me.mfransen.openmonsters.launcher.gui.ProgressDialog;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by matt on 12/10/15.
 */
public class Updater {
    public static int getVersion() {
        int result = 0;
        try {
            FileInputStream fis = new FileInputStream(new File(Main.dataFolder,"version.txt"));
            Scanner scnr = new Scanner(fis);
            result = scnr.nextInt();
            scnr.close();
            fis.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return result;
    }
    public static int getLatestVersion() {
        int result = 0;
        try {
            URL url = new URL(LauncherConfig.gameDownload+"version.txt");
            InputStream is = url.openStream();
            Scanner scnr = new Scanner(is);
            result = scnr.nextInt();
            scnr.close();
            is.close();
        } catch (IOException e) {
        }
        return result;
    }
    public static void Update(int version) {
        new File(Main.dataFolder,"bin").delete();
        new File(Main.dataFolder,"lib").delete();
        try {
            URL url = new URL(LauncherConfig.gameDownload+version+".zip");
            ProgressDialog.showDialog(url.openStream(),new File(Main.dataFolder,"Cache"+File.separator+version+".zip"),url.openConnection().getContentLength(),Main.dataFolder,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
