package me.mfransen.openmonsters.launcher;

import me.mfransen.openmonsters.launcher.gui.ProgressDialog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by matt on 12/10/15.
 */
public class Updater {
    public static Version getVersion() {
        String result = "v0.0.0";
        try {
            FileInputStream fis = new FileInputStream(new File(Main.dataFolder,"version.txt"));
            Scanner scnr = new Scanner(fis);
            result = scnr.nextLine();
            scnr.close();
            fis.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        result = result.substring(1);
        return new Version(result);
    }
    private static JSONObject getLatestVersionInfo() throws ParseException, IOException {
        URL url = new URL("https://api.github.com/repos/mfransen1997/OpenMonsters/releases/latest");
        Scanner scnr = new Scanner(url.openStream());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(scnr.useDelimiter("\\Z").next());
    }
    public static Version getLatestVersion() {
        String result = "v0.0.0";
        try {
            result = getLatestVersionInfo().get("tag_name").toString();
        } catch (IOException e) {
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Version(result.substring(1));
    }
    public static void Update() {
        new File(Main.dataFolder,"bin").delete();
        try {
            JSONObject version = getLatestVersionInfo();
            JSONArray array = (JSONArray)version.get("assets");
            Iterator<Object> it = array.iterator();
            JSONObject asset = null;
            while(it.hasNext()) {
                JSONObject object = (JSONObject)it.next();
                if(object.get("name").toString().equalsIgnoreCase("data.zip"))
                    asset = object;
                else
                    it.remove();
            }
            if(asset!=null) {
                URL url = new URL(asset.get("browser_download_url").toString());
                ProgressDialog.showDialog(url.openStream(),new File(Main.dataFolder,"Cache"+File.separator+version.get("tag_name").toString()+".zip"),url.openConnection().getContentLength(),Main.dataFolder,false);
            }
            File versionFile = new File(Main.dataFolder,"version.txt");
            versionFile.delete();
            FileWriter fw = new FileWriter(versionFile.getAbsoluteFile());
            fw.write(version.get("tag_name").toString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
