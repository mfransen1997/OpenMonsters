package me.mfransen.openmonsters.assets;

import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

/**
 * Created by matt on 12/11/15.
 */
public interface AssetLoader<T> {
    Class<? extends T> type();
    T load(AssetManager assets, byte[] data);
}
