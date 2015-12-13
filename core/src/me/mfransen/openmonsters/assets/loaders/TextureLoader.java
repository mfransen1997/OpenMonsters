package me.mfransen.openmonsters.assets.loaders;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import me.mfransen.openmonsters.assets.AssetLoader;
import me.mfransen.openmonsters.assets.AssetManager;

/**
 * Created by matt on 12/11/15.
 */
public class TextureLoader implements AssetLoader<Texture> {
    @Override
    public Class<? extends Texture> type() {
        return Texture.class;
    }

    @Override
    public Texture load(AssetManager assets, byte[] data) {
        return new Texture(new Pixmap(data,0,data.length));
    }
}
