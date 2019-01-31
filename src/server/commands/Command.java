package server.commands;

import server.Server;

/**
 * Created by ASUS on 30/01/2019.
 */
public abstract class Command {

    public String name;

    public Command(String name){
        this.name = name;
    }

    public abstract boolean execute(Server server, String[] args);

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
