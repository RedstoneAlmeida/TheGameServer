package server.commands.defaults;

import server.Server;
import server.commands.Command;

/**
 * Created by ASUS on 30/01/2019.
 */
public class TestCommand extends Command {

    public TestCommand() {
        super("Test");
    }

    @Override
    public boolean execute(Server server, String[] args) {
        StringBuilder builder = new StringBuilder();
        for(String a : args){
            builder.append(a).append(" ");
        }
        System.out.println(builder.toString());
        return false;
    }
}
