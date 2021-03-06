package me.mfransen.openmonsters.api.addons;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

/**
 * Created by matt on 12/7/15.
 */
public class AddonLoader {
    public Addon loadAddon(File f) throws IOException, JAXBException {
        ZipFile zf = new ZipFile(f);
        JAXBContext context = JAXBContext.newInstance(AddonInfo.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AddonInfo info = (AddonInfo) unmarshaller.unmarshal(zf.getInputStream(zf.getEntry("addon.xml")));
        Logger.getLogger("OpenMonsters").info(String.format("Loaded addon '%1s' v%2s by %3s", info.getId(), info.getVersion(), info.getAuthor()));
        return new Addon(info);
    }
}
