package me.mfransen.openmonsters.launcher;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

@XmlRootElement(name="Addon")
public class AddonInfo {
    private String id;
    private String version;
    private String author;
    private String updateSite;
    public RepositoryAddonInfo downloadInfo;
    public File folder;
    @XmlElement
    private void setId(String id) {
        this.id = id;
    }
    @XmlElement
    private void setVersion(String version) {
        this.version = version;
    }
    @XmlElement
    private void setAuthor(String author) {
        this.author = author;
    }
    @XmlElement(name="update-site")
    private void setUpdateSite(String updateSite) {
        this.updateSite = updateSite;
    }
    public String getId() {
        return id;
    }
    public String getAuthor() { return author; }
    public String getVersion() { return version; }
    public String getUpdateSite() { return updateSite; }
}
