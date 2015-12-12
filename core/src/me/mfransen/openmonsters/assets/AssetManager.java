package me.mfransen.openmonsters.assets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import me.mfransen.openmonsters.assets.loaders.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by matt on 12/11/15.
 */
public class AssetManager {
    private Map<String,Object> objects = new HashMap<String, Object>();
    private Map<Class<?>,AssetLoader> loaders = new HashMap<Class<?>, AssetLoader>();
    public Map<String,Class<?>> mimeTypes = new HashMap<String, Class<?>>();
    public AssetManager() {
        loadDefaultLoaders();
    }
    private void loadDefaultLoaders() {
        loaders.put(Texture.class,new TextureLoader());
        mimeTypes.put("png",Texture.class);
        mimeTypes.put("jpg",Texture.class);
    }
    public boolean load(File f, String name, Class<?> type) throws IOException {
        return load(new FileInputStream(f),name,type);
    }
    public boolean load(InputStream is, String name, Class<?> type) throws IOException {
        ByteOutputStream bos = new ByteOutputStream();
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        while(len!=-1) {
            bos.write(buffer,0,len);
            len = is.read(buffer);
        }
        is.close();
        byte[] response = bos.getBytes();
        return load(response,name,type);
    }
    public boolean load(byte[] data, String name, Class<?> type) {
        Class<?> t = type;
        if(loaders.containsKey(type)) {
            objects.put(name, loaders.get(type).load(data));
            return true;
        } else
            return false;
    }
    public <T> T get(String name) {
        return (T)objects.get(name);
    }
    public boolean isLoaded(String name) {
        return objects.containsKey(name);
    }
    public int size() {
        return objects.size();
    }
    public Set<String> loadedAssets() {
        return objects.keySet();
    }
    public void registerLoader(AssetLoader loader) {
        loaders.put(loader.type(),loader);
    }
    public boolean containsLoader(Class<?> type) {
        return loaders.containsKey(type);
    }
    public void loadDirectory(File directory) {
        loadDirectory(directory,"");
    }
    public void loadDirectory(File directory,String prefix) {
        for(File f : directory.listFiles()) {
            if(f.isDirectory())
                loadDirectory(f,prefix+File.separator+f.getName());
            else {
                String extension = getExtension(f.getName());
                if(mimeTypes.containsKey(extension)) {
                    Class<?> mimeType = mimeTypes.get(extension);
                    if(containsLoader(mimeType))
                        try {
                            load(f,f.getName().substring(0,f.getName().length()-extension.length()-1),mimeType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }
    public static String getExtension(String file) {
        String extension = "";
        int i = file.lastIndexOf('.');
        if (i > 0) {
            extension = file.substring(i+1);
        }
        return extension;
    }
}
