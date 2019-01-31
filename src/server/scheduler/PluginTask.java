package server.scheduler;

import server.Server;
import server.plugin.MethodManager;

/**
 * Created by ASUS on 27/01/2019.
 */
public class PluginTask extends Task {

    private String functionName;
    private Object[] args;
    private MethodManager m;

    public PluginTask(MethodManager m, String functionName, Object ...args){
        this.m = m;
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public void onRun() {
        this.m.callAll(functionName, args);
    }

    @Override
    public void onCompletion(Server server) {

    }
}
