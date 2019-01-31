package server.plugin;

import server.OpenServer;
import server.Server;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ASUS on 20/01/2019.
 */
public class JavaPluginLoader {

    private Server server;
    private ScriptEngine engine;
    private MethodManager manager;

    private Map<PluginEntry, File> plugins = new HashMap<>();

    public JavaPluginLoader(Server server){
        this.server = server;
        this.manager = new MethodManager(this);
        final ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByMimeType("text/javascript");
        engine.put("server", server);
        engine.put("manager", this.manager);
        engine.put("scheduler", server.getScheduler());
        engine.put("network", server.getNetwork());
    }

    public int checkPlugins(){
        File file = new File(OpenServer.PLUGIN_PATH);
        if(!file.isDirectory())
            return 0;
        int i = 0;
        File[] files = file.listFiles();
        if(files == null)
            return 0;
        for(File folder : files){
            if(!folder.isDirectory()) continue;
            //File next = new File(folder + "/plugin.yml");
            for (File file1 : Objects.requireNonNull(folder.listFiles())) {
                if(file1.isDirectory()) continue;
                if(file1.getName().contains(".js")){
                    try (final Reader reader = new InputStreamReader(new FileInputStream(file1))) {
                        //System.out.println("Loaded Script: " + file1.getName());
                        engine.put(file1.getName().replace(".", ""), folder);

                        PluginDescription desc = new PluginDescription(folder);
                        System.out.println(String.format("Carregando %s v%s", desc.getName(), desc.getVersion()));
                        engine.eval(reader);

                        plugins.put(new PluginEntry(desc.getName(), desc.getVersion()), folder);

                        this.call("onEnable", desc);

                        //System.out.println(file1.getName().replace(".js", ""));
                        i++;
                    } catch (final Exception e) {
                        System.err.println("Could not load " + file1.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
        return i;
    }

    public synchronized void call(String functionName, Object... args){
        if(engine.get(functionName) == null){
            return;
        }
        try {
            ((Invocable) engine).invokeFunction(functionName, args);
        } catch (final Exception se) {
            System.out.printf("Error while calling " + functionName, se);
            se.printStackTrace();
        }
    }

    public synchronized Object callObject(String functionName, Object... args){
        if(engine.get(functionName) == null){
            return null;
        }
        try {
            return ((Invocable) engine).invokeFunction(functionName, args);
        } catch (final Exception se) {
            System.out.printf("Error while calling " + functionName, se);
            se.printStackTrace();
        }
        return null;
    }

    public Server getServer() {
        return server;
    }
}
