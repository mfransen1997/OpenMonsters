package me.mfransen.openmonsters;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by matt on 12/10/15.
 */
public class TextureManager {
    private Map<String,Texture> textures = new HashMap<String, Texture>();
    public void load(File f, String name) throws IOException {
        load(new FileInputStream(f),name);
    }
    public void load(InputStream is, String name) throws IOException {
        ByteOutputStream bos = new ByteOutputStream();
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        while(len!=-1) {
            bos.write(buffer,0,len);
            len = is.read(buffer);
        }
        is.close();
        byte[] response = bos.getBytes();
        Pixmap pixmap = new Pixmap(response,0,response.length);
        textures.put(name,new Texture(pixmap));
    }
    public Texture get(String name) {
        return textures.get(name);
    }
    public boolean isLoaded(String name) {
        return textures.containsKey(name);
    }
    public int size() {
        return textures.size();
    }
    public Set<String> loadedTextures() {
        return textures.keySet();
    }
}
