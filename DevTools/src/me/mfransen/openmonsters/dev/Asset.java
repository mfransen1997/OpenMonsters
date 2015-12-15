package me.mfransen.openmonsters.dev;

import java.io.File;

/**
 * Created by matt on 12/15/15.
 */
public class Asset {
    private String name;
    private File file;
    public Asset(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
