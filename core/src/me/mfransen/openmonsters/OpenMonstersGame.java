package me.mfransen.openmonsters;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.mfransen.openmonsters.assets.AssetManager;
import me.mfransen.openmonsters.map.Map;
import me.mfransen.openmonsters.map.MapLayer;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class OpenMonstersGame extends ApplicationAdapter {
	SpriteBatch batch;
	AssetManager assets = new AssetManager();
    Map m;
	public static File dataFolder;
    public static Logger logger;
	@Override
	public void create () {
        logger = Logger.getLogger("OpenMonsters");
		batch = new SpriteBatch();
        dataFolder = new File(getDataFolder(System.getProperty("os.name")));
        loadDefaultAssets();
        m = new Map((Texture)assets.get("tileset"),8,7,32);
        MapLayer layer = new MapLayer(m);
        layer.tiles[0][0]=32;
        layer.tiles[1][0]=33;
        layer.tiles[2][0]=34;
        layer.tiles[0][1]=16;
        layer.tiles[1][1]=17;
        layer.tiles[2][1]=18;
        layer.tiles[0][2]=0;
        layer.tiles[1][2]=1;
        layer.tiles[2][2]=2;
        m.layers.add(layer);
	}
	private void loadDefaultAssets() {
        File source = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if(source.isDirectory()) {
            File resources = new File(System.getProperty("user.dir")+File.separator+"assets");
            logger.info("Development Environment Detected; loading assets from working directory");
            assets.loadDirectory(resources);
        } else {
            try {
                JarFile jar = new JarFile(source);
                Enumeration<JarEntry> entries = jar.entries();
                while(entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if(entry.getName().startsWith("assets/")&&!entry.isDirectory()) {
                        String ext = AssetManager.getExtension(entry.getName());
                        if(assets.mimeTypes.containsKey(ext))
                            assets.load(jar.getInputStream(entry),entry.getName().substring(7,entry.getName().lastIndexOf(".")),assets.mimeTypes.get(ext));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getDataFolder(String os) {
        if(os.toLowerCase().contains("windows")) {
            return System.getenv("APPDATA")+ File.separator+"OpenMonsters";
        } else if(os.toLowerCase().contains("mac")) {
            return System.getProperty("user.home")+File.separator+"Application Support"+File.separator+"OpenMonsters";
        } else {
            return System.getProperty("user.home")+File.separator+"OpenMonsters";
        }
    }
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        m.drawMap(batch,0,0);
		batch.end();
	}
}
