package server.commands;

import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ASUS on 30/01/2019.
 */
public class CommandReader {

    private Server server;

    public CommandReader(Server server){
        this.server = server;
    }

    public void init(){
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                // Le entao a palagra SAIR nao seja digitada
                String linha = "";
                Command command;
                while (!linha.equals("SAIR")) {
                    linha = reader.readLine().toLowerCase().replace("/", "");
                    String[] split = linha.split(" ");
                    command = this.server.getCommandMap().getCommand(split[0]);
                    if(command != null) {
                        command.execute(this.server, split);
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Erro: "+ e);
            }
        }).start();
    }

}
