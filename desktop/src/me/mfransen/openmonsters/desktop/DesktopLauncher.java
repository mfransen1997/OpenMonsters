package me.mfransen.openmonsters.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.mfransen.openmonsters.OpenMonstersGame;
import me.mfransen.openmonsters.api.addons.AddonLoader;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		AddonLoader loader = new AddonLoader();
		try {
			System.out.println(loader.loadAddon(new File("/home/matt/Desktop/test.zip")).info.getId());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.allowSoftwareMode=true;
		new LwjglApplication(new OpenMonstersGame(), config);
	}
}
