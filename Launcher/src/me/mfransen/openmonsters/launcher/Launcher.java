package me.mfransen.openmonsters.launcher;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by matt on 12/10/15.
 */
public class Launcher {
    public static void launch() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        URLClassLoader loader = new URLClassLoader(new URL[]{new File(Main.dataFolder,"bin"+File.separator+"launch.jar").toURI().toURL()});
        Class<?> clazz = loader.loadClass("me.mfransen.openmonsters.desktop.DesktopLauncher");
        String[] args = new String[0];
        clazz.getDeclaredMethod("main",args.getClass()).invoke(null,new Object[]{args});
    }
}
