package me.mfransen.openmonsters.api.addons;

import org.w3c.dom.Element;

/**
 * Created by Matt Fransen on 11/30/2015.
 */
public class AddonInfo{
    private String id;
    private String version;
    private String author;
    private String updateSite;
    public AddonInfo(String id, String version, String author, String updateSite) {
        this.id = id;
        this.version = version;
        this.author = author;
        this.updateSite = updateSite;
    }
    public AddonInfo(Element element) {
        this.id = element.getAttribute("id");
        this.version = element.getAttribute("version");
        if(element.hasAttribute("author")) {
            this.author = element.getAttribute("author");
        }
        if(element.hasAttribute("updateSite")) {
            this.updateSite = element.getAttribute("updateSite");
        }
    }
    public void serialize(Element element) {
        element.setAttribute("id",id);
        element.setAttribute("version",version);
        element.setAttribute("author",author);
        element.setAttribute("updateSite",updateSite);
    }
}
