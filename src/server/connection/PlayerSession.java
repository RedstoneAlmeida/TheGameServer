package server.connection;

import network.protocol.DataPacket;
import server.Player;
import server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerSession {

    private Server server;
    private Socket socket;
    private Player player = null;

    private ServerSimpleAdapter adapter;

    private ConcurrentLinkedQueue<DataPacket> pool = new ConcurrentLinkedQueue<>();

    public PlayerSession(Server server, Socket socket){
        this.server = server;
        this.socket = socket;

        this.adapter = new ServerSimpleAdapter(this);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Socket getSocket() {
        return socket;
    }

    public Server getServer() {
        return server;
    }

    public void putPoolPacket(DataPacket packet){
        pool.add(packet);
    }

    public void writeDataPacket(DataPacket packet){
        packet.encode();
        byte[] buffer = packet.getBuffer();
        try {
            this.adapter.write(packet.pid(), buffer, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(){
        if(this.socket == null || this.socket.isClosed())
            return;
        try {
            this.adapter.read();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public DataPacket getPoolPacket(){
        if(!pool.isEmpty())
            return pool.poll();
        return null;
    }
}
