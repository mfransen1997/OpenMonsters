package me.mfransen.openmonsters.launcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class SimpleAddonLoader {
    public List<AddonInfo> addons = new ArrayList<AddonInfo>();
    public void loadAddon(File f) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(AddonInfo.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AddonInfo info = (AddonInfo) unmarshaller.unmarshal(new File(f,"addon.xml"));
        addons.add(info);
    }
    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    public void loadAddons(File dir) throws IOException, JAXBException {
        for(File f : dir.listFiles()) {
            if(f.isDirectory())
                loadAddon(f);
            else if(f.getName().endsWith(".zip")) {
                if(!new File(Main.addonsFolder,f.getName().substring(0,f.getName().lastIndexOf('.'))).exists()) {
                    extractAddon(f);
                    f.delete();
                    Main.logger.info("Extracted "+f.getName());
                    loadAddon(new File(Main.addonsFolder,f.getName().substring(0,f.getName().lastIndexOf('.'))));
                }
            }
        }
    }



    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void extractAddon(File zip) throws IOException {
        File folder = new File(Main.addonsFolder,zip.getName().substring(0,zip.getName().lastIndexOf('.')));
        folder.mkdir();
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        ZipEntry entry = zis.getNextEntry();
        while(entry!=null) {
            String filename = entry.getName();
            File output = new File(folder,filename);
            output.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(output);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            entry = zis.getNextEntry();
        }
    }
}
