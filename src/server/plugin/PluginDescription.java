package server.plugin;

import network.properties.Config;

import java.io.File;

/**
 * Created by ASUS on 20/01/2019.
 */
public class PluginDescription {

    private String name;
    private String version;

    public PluginDescription(File folder){
        Config config = new Config(folder + "/plugin.yml");
        if(config.exists("name"))
            this.name = config.getString("name");
        if(config.exists("version"))
            this.version = config.getString("version");
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
