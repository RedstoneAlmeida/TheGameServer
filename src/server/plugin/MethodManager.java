package server.plugin;

import server.commands.defaults.PluginCommand;
import server.scheduler.PluginTask;

import java.io.File;

/**
 * Created by ASUS on 20/01/2019.
 */
public class MethodManager {

    private JavaPluginLoader loader;

    public MethodManager(JavaPluginLoader loader){
        this.loader = loader;
    }

    public void send(String message){
        System.out.println(message);
    }

    public void callAll(String functionName, Object... args){
        this.loader.call(functionName, args);
    }

    public PluginDescription getDescription(File folder){
        return new PluginDescription(folder);
    }

    public int addScheduler(String functionName, int delay){
        return loader.getServer().getScheduler().addScheduler(new PluginTask(this, functionName), delay);
    }

    public int addScheduler(String functionName, int delay, Object ...args){
        return loader.getServer().getScheduler().addScheduler(new PluginTask(this, functionName, args), delay);
    }

    public void addCommand(String name, String functionName){
        this.loader.getServer().getCommandMap().addCommand(new PluginCommand(name, functionName));
    }

}
