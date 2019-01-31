package server.commands;

import server.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 30/01/2019.
 */
public class CommandMap {

    private Map<String, Command> commandMap = new HashMap<>();

    private Server server;

    public CommandMap(Server server){
        this.server = server;
    }

    public void addCommand(Command command){
        this.commandMap.put(command.getName().toLowerCase(), command);
    }

    public Command getCommand(String prefix){
        if(this.commandMap.containsKey(prefix))
            return this.commandMap.get(prefix);
        return null;
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
}
