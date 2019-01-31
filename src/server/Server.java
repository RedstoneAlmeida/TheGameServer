package server;

import network.Network;
import network.protocol.HandlerPacket;
import server.commands.CommandMap;
import server.commands.CommandReader;
import server.commands.defaults.TestCommand;
import server.entity.Entity;
import server.plugin.JavaPluginLoader;
import server.scheduler.ServerScheduler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ASUS on 12/01/2019.
 */
public class Server {

    // Dados do Servidor
    private InetSocketAddress address;
    private Network network;

    // Scheduler
    private ServerScheduler scheduler;

    private Map<Long, Entity> entities = new ConcurrentHashMap<>();
    private Map<Long, Player> players = new ConcurrentHashMap<>();
    private Map<Socket, Player> socketmap = new ConcurrentHashMap<>();

    private JavaPluginLoader loader;

    // Commands
    private CommandReader commandReader;
    private CommandMap commandMap;

    private static Server instance = null;

    private int tick = 60;

    private long lastTime = System.currentTimeMillis();

    public Server(InetSocketAddress address){
        this.address = address;
        this.network = new Network();
        this.scheduler = new ServerScheduler(this);
        instance = this;
        this.commandReader = new CommandReader(this);
        this.commandMap = new CommandMap(this);
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }

    public ServerScheduler getScheduler() {
        return scheduler;
    }

    public static Server getInstance() {
        return instance;
    }

    public void start() throws IOException {
        ServerSocket serv = new ServerSocket();
        serv.bind(address);
        this.initialize();
        tick();
        new Thread(() -> {
            while (true){
                try {
                    Socket socket = serv.accept();
                    long eid = Entity.EntityCount++;
                    Player player = new Player(Server.this, socket, eid);
                    players.put(eid, player);
                    //entities.put(eid, player);
                    socketmap.put(socket, player);

                    System.out.println(String.format("Sessão iniciada com %s sua ID é %s", socket.getInetAddress().getHostAddress(), eid));
                    //System.out.println("EntityID: " + eid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initialize(){
        new File(OpenServer.PLUGIN_PATH).mkdir();
        System.out.println(String.format("Inicializando servidor em %s:%s", address.getAddress().getHostAddress(), address.getPort()));

        loader = new JavaPluginLoader(this);
        System.out.println("Carregando plugins");
        int count = loader.checkPlugins();
        if(count > 0)
            System.out.println("Carregado um total de " + count + " plugins");
        lastTime = System.currentTimeMillis();
        this.initCommands();
        this.commandReader.init();
    }

    public void initCommands(){
        this.commandMap.addCommand(new TestCommand());
    }

    public void tick(){
        new Thread(() -> {
            while (true){
                long time = System.currentTimeMillis();
                for(long eid : players.keySet()) {
                    Player player = players.get(eid);
                    try {
                        player.tick();
                    } catch (Exception e){
                        players.remove(eid);
                    }
                }
                long finaltime = this.lastTime = System.currentTimeMillis();

                try {
                    this.loader.call("serverTick", this, finaltime - time);
                } catch (Exception e){

                }
            }
        }).start();
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(50);
                    scheduler.tick();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public JavaPluginLoader getLoader() {
        return loader;
    }

    public Network getNetwork() {
        return network;
    }

    public int countofPlayers(){
        return players.values().size();
    }

    public Map<Long, Player> getOnlinePlayers(){
        return players;
    }

    public Map<Long, Entity> getEntities() {
        return entities;
    }
}
