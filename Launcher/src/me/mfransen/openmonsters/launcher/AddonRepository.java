package me.mfransen.openmonsters.launcher;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 12/8/15.
 */
public class AddonRepository {
    public Map<String,RepositoryAddonInfo> addons = new HashMap<String, RepositoryAddonInfo>();

    public AddonRepository(Document doc) {
        parse(doc);
    }
    public AddonRepository() {
    }
    private void parse(Document doc) {
        Element e = doc.getDocumentElement();
        if(e.getTagName().equalsIgnoreCase("repository")) {
            NodeList nodeList = e.getChildNodes();
            for(int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if(childNode.getNodeName().equalsIgnoreCase("Addon")) {
                    parseAddon((Element)childNode);
                }
            }
        }
    }
    private void parseAddon(Element element) {
        String name = element.getAttribute("name");
        String version = element.getAttribute("version");
        String download = element.getAttribute("download");
        addons.put(name,new RepositoryAddonInfo(version,download));
    }
}
