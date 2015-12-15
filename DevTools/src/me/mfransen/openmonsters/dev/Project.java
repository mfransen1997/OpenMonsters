package me.mfransen.openmonsters.dev;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 12/14/15.
 */
public class Project extends DefaultTreeModel{
    private Map<String,Asset> textures = new HashMap<String, Asset>();
    private Map<String,Asset> maps = new HashMap<String, Asset>();
    private Map<String,Asset> assets = new HashMap<String, Asset>();
    private File projectRoot;
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        ((DefaultMutableTreeNode)getRoot()).setUserObject(name);
        nodeChanged((TreeNode)getRoot());
    }
    public Project(String name) {
        super(new DefaultMutableTreeNode(name));
        this.name = name;
    }
    public Project(File f) throws IOException, SAXException, ParserConfigurationException {
        super(new DefaultMutableTreeNode(f.getName()));
        if(getExt(f.getName()).equalsIgnoreCase("omp")) {
            projectRoot = f.getParentFile();
            load(f);
            createStructure(f.getParentFile());
        } else if(f.isDirectory()) {
            File project = new File(f,"project.omp");
            projectRoot = f;
            if(project.exists()) {
                load(project);
                createStructure(f);
            }
        }
    }
    private String getExt(String file) {
        return file.substring(file.lastIndexOf(".")+1,file.length());
    }
    private void load(File f) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(f);
        NodeList textures = ((Element) doc.getDocumentElement().getElementsByTagName("Textures").item(0)).getElementsByTagName("Asset");
        NodeList maps = ((Element) doc.getDocumentElement().getElementsByTagName("Maps").item(0)).getElementsByTagName("Asset");
        NodeList assets = ((Element) doc.getDocumentElement().getElementsByTagName("Assets").item(0)).getElementsByTagName("Asset");
        DefaultMutableTreeNode texturesNode = new DefaultMutableTreeNode("Textures");
        DefaultMutableTreeNode mapsNode = new DefaultMutableTreeNode("Maps");
        DefaultMutableTreeNode assetsNode = new DefaultMutableTreeNode("Assets");
        for(int i = 0; i < textures.getLength(); i++) {
            Element e = (Element) textures.item(i);
            Asset asset = new Asset(e.getAttribute("name"),new File(projectRoot,"Textures"+File.separator+e.getAttribute("location")));
            this.assets.put(asset.getName(),asset);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(asset);
            texturesNode.add(node);
        }
        for(int i = 0; i < maps.getLength(); i++) {
            Element e = (Element) textures.item(i);
            Asset asset = new Asset(e.getAttribute("name"),new File(projectRoot,"Maps"+File.separator+e.getAttribute("location")));
            this.assets.put(asset.getName(),asset);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(asset);
            mapsNode.add(node);
        }
        for(int i = 0; i < assets.getLength(); i++) {
            Element e = (Element) textures.item(i);
            Asset asset = new Asset(e.getAttribute("name"),new File(projectRoot,"Assets"+File.separator+e.getAttribute("location")));
            this.assets.put(asset.getName(),asset);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(asset);
            assetsNode.add(node);
        }
        ((DefaultMutableTreeNode)getRoot()).add(assetsNode);
        ((DefaultMutableTreeNode)getRoot()).add(mapsNode);
        ((DefaultMutableTreeNode)getRoot()).add(texturesNode);
        setName(doc.getDocumentElement().getAttribute("name"));
    }
    public void createStructure(File dir) {
        File textures = new File(dir,"Textures");
        File maps = new File(dir,"Maps");
        if(!textures.exists())
            textures.mkdirs();
        if(!maps.exists())
            maps.mkdirs();
    }
}

