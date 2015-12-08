package me.mfransen.openmonsters.desktop;

import me.mfransen.openmonsters.api.addons.AddonLoader;
import me.mfransen.openmonsters.desktop.gui.LauncherWindow;

import javax.swing.*;

public class DesktopLauncher {
	public static void main (String[] arg) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
		AddonLoader loader = new AddonLoader();
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.allowSoftwareMode=true;
		new LwjglApplication(new OpenMonstersGame(), config);*/
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		LauncherWindow window = new LauncherWindow();
		window.pack();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);

	}
}
