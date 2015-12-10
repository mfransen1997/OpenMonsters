package me.mfransen.openmonsters;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OpenMonstersGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureManager textures = new TextureManager();
	public static File dataFolder;
	@Override
	public void create () {
		batch = new SpriteBatch();
        dataFolder = new File(getDataFolder(System.getProperty("os.name")));
		try {
			textures.load(OpenMonstersGame.class.getResourceAsStream("assets/badlogic.png"),"badlogic");
			img = textures.get("badlogic");
		} catch (IOException e) {
			e.printStackTrace();
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
		batch.draw(img, 0, 0);
		batch.end();
	}
}
