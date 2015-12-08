package me.mfransen.openmonsters.launcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * Created by matt on 12/8/15.
 */
public class SimpleAddonLoader {
    public List<AddonInfo> addons = new ArrayList<AddonInfo>();
    public void loadAddon(File f) throws IOException, JAXBException {
        ZipFile zf = new ZipFile(f);
        JAXBContext context = JAXBContext.newInstance(AddonInfo.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AddonInfo info = (AddonInfo) unmarshaller.unmarshal(zf.getInputStream(zf.getEntry("addon.xml")));
        addons.add(info);
    }
    public void loadAddons(File dir) throws IOException, JAXBException {
        for(File f : dir.listFiles()) {
            loadAddon(f);
        }
    }
}
