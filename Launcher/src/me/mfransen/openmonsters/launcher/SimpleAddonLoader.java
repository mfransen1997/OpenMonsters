package me.mfransen.openmonsters.launcher;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class SimpleAddonLoader {
    public List<AddonInfo> addons = new ArrayList<AddonInfo>();
    public void loadAddon(File f) throws IOException, JAXBException, ParserConfigurationException, SAXException {
        JAXBContext context = JAXBContext.newInstance(AddonInfo.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        if(!new File(f,"addon.xml").exists()) {
            Main.logger.info("Unable to load "+f.getPath()+" (no addon.xml)");
            return;
        }
        AddonInfo info = (AddonInfo) unmarshaller.unmarshal(new File(f,"addon.xml"));
        if(info.getUpdateSite()!=null&&!info.getUpdateSite().isEmpty())
            getUpdateInfo(info);
        info.folder = f;
        addons.add(info);
    }
    public void loadAddon(InputStream stream) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(AddonInfo.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AddonInfo info = (AddonInfo) unmarshaller.unmarshal(stream);
        addons.add(info);
    }
    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    public void loadAddons(File dir) throws IOException, JAXBException, ParserConfigurationException, SAXException {
        for(File f : dir.listFiles()) {
            if(f.isDirectory()) {
                loadAddon(f);
            }
            else if(f.getName().endsWith(".zip")) {
                if(!new File(Main.addonsFolder,f.getName().substring(0,f.getName().lastIndexOf('.'))).exists()) {
                    extractAddon(f);
                    f.delete();
                    Main.logger.info("Extracted "+f.getName());
                    loadAddon(new File(Main.addonsFolder,f.getName().substring(0,f.getName().lastIndexOf('.'))));
                } else {
                    Main.logger.warning("Unable to extract, "+f.getName()+", folder already exists.");
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void extractAddon(File zip) throws IOException {
        extractAddon(zip, new File(Main.addonsFolder,zip.getName().substring(0,zip.getName().lastIndexOf('.'))));
    }
    public void extractAddon(File zip, File folder) throws IOException {
        if(!folder.exists())
            folder.mkdir();
        extractAddon(new FileInputStream(zip),folder);
    }
    public void extractAddon(InputStream input, File folder) throws IOException {
        ZipInputStream zis = new ZipInputStream(input);
        ZipEntry entry = zis.getNextEntry();
        while(entry!=null) {
            if(entry.isDirectory()) {
                new File(folder,entry.getName()).mkdirs();
            } else {
                String filename = entry.getName();
                File output = new File(folder, filename);
                output.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(output);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            Main.logger.info("Extracted: "+entry.getName());
            entry = zis.getNextEntry();
        }
        input.close();
    }
    public void getUpdateInfo(AddonInfo info) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL(info.getUpdateSite());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        AddonRepository repo = new AddonRepository(db.parse(info.getUpdateSite()));
        if(repo.addons.containsKey(info.getId()))
            info.downloadInfo = repo.addons.get(info.getId());

    }
}
