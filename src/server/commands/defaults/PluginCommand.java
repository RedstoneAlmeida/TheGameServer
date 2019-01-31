package server.commands.defaults;

import server.Server;
import server.commands.Command;

/**
 * Created by ASUS on 30/01/2019.
 */
public class PluginCommand extends Command {

    private String functionName;

    public PluginCommand(String name, String functionName) {
        super(name);
        this.functionName = functionName;
    }

    @Override
    public boolean execute(Server server, String[] args) {
        server.getLoader().call(functionName, server, args);
        return false;
    }
}
