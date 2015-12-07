package me.mfransen.openmonsters.api.addons;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

/**
 * Created by matt on 12/7/15.
 */
public class AddonLoader {
    public void loadAddon(File f) throws IOException {
        ZipFile zf = new ZipFile(f);
    }
}
