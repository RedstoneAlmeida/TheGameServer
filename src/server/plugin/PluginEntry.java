package server.plugin;

/**
 * Created by ASUS on 27/01/2019.
 */
public class PluginEntry {

    private String name;
    private String version;

    public PluginEntry(String name, String version){
        this.name = name;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }
}
