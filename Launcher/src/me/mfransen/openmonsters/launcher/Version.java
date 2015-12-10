package me.mfransen.openmonsters.launcher;

/**
 * Created by matt on 12/9/15.
 */
public class Version {
    public int major;
    public int minor;
    public int build;
    public Version(int major, int minor, int build) {
        this.major = major;
        this.minor = minor;
        this.build = build;
    }
    public Version(int major, int minor) {
        this(major, minor, 0);
    }
    public Version(int major) {
        this(major, 0);
    }
    public Version() {
        this(0);
    }
    public Version(String version) {
        String[] parts = version.split("\\.");
        major = Integer.parseInt(parts[0]);
        minor = Integer.parseInt(parts[1]);
        build = Integer.parseInt(parts[2]);
    }
    public boolean isOlder(Version v) {
        if(v.major>major)
            return true;
        else if(v.major==major&&v.minor>minor)
            return true;
        else if(v.major==major&&v.minor==minor&&v.build>build)
            return true;
        else
            return false;
    }
    public boolean isOlder(String version) {
        return isOlder(new Version(version));
    }
}
