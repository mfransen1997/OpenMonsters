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
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.allowSoftwareMode=true;
		new LwjglApplication(new OpenMonstersGame(), config);*/
	}
}
