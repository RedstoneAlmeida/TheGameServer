package server;

import network.message.MessageConnection;
import network.protocol.*;
import server.connection.PlayerSession;
import server.entity.Entity;
import server.event.connection.ReceivePacketEvent;
import server.inventory.BaseInventory;
import utils.AnimationType;
import utils.PacketString;
import utils.TextType;

import java.net.Socket;

/**
 * Created by ASUS on 13/01/2019.
 */
public class Player extends Entity {

    private PlayerSession session;
    private String username = "";

    private long eid = 0L;

    private boolean isSpawned = false;

    //private BaseInventory inventory = new BaseInventory(this);

    public Player(Server server, Socket socket, long eid){
        this.session = new PlayerSession(server, socket);
        this.session.setPlayer(this);
        this.eid = eid;

        this.sendRuntime();
        this.sendAllPlayers();

        this.isSpawned = true;
    }

    public boolean isSpawned() {
        return isSpawned;
    }



    /**
     * Voce; pode reescrever a forma de leitura de pacotes caso queira
     * Basta extender o PlayerSession e jogar aqui
     *
     * player.setSession(yourSession);
     */
    public void setSession(PlayerSession session) {
        this.session = session;
    }

    public PlayerSession getSession() {
        return session;
    }

    protected void tick(){
        this.readLastPacket();

        this.updateMovement();

        this.session.getServer().getLoader().call("playerTick", this);
    }

    private void readLastPacket(){
        this.session.read();
        DataPacket packet = session.getPoolPacket();
        handlePacket(packet);
    }

    public void dataPacket(DataPacket packet){
        //System.out.println(String.format("Socket Send: %s -> %s", session.getSocket().getInetAddress().getHostAddress(), PacketString.getString(packet.pid())));
        this.session.writeDataPacket(packet);
    }

    public void handlePacket(DataPacket packet) {
        if (packet == null)
            return;
        ReceivePacketEvent event = new ReceivePacketEvent();
        session.getServer().getLoader().call("receivePacketEvent", this, packet, event);
        if(event.isCancelled())
            return;
        switch (packet.pid()) {
            case ProtocolInfo.HANDLER_PACKET:
                this.dataPacket(new HandlerPacket());
                break;
            case ProtocolInfo.GAME_START_PACKET:
                GameStartPacket gameStartPacket = (GameStartPacket) packet;
                this.username = gameStartPacket.name;
                System.out.println("Received User: " + this.username);
                //this.sendRuntime();
                //this.sendAllPlayers();
                break;
            case ProtocolInfo.MOVE_PLAYER_PACKET:
                MoveEntityPacket moveEntityPacket = (MoveEntityPacket) packet;
                long id = moveEntityPacket.eid;
                int x = moveEntityPacket.x;
                int y = moveEntityPacket.y;

                int lastx = this.getX();
                int lasty = this.getY();
                this.setX(x);
                this.setY(y);

                if(lastx == x && lasty == y) {
                    this.sendAnimation(AnimationType.ANIMATION_STOPPED);
                    return;
                }

                if(lasty < y)
                    this.sendAnimation(AnimationType.ANIMATION_JUMP);
                else
                    this.sendAnimation(AnimationType.ANIMATION_GROUND);

                if(lastx < x)
                    this.sendAnimation(AnimationType.ANIMATION_RIGHT);
                else
                    this.sendAnimation(AnimationType.ANIMATION_LEFT);

                this.updateMovement();
                break;
            case ProtocolInfo.ANIMATION_PACKET:
                AnimationPacket animationPacket = (AnimationPacket) packet;
                this.sendAnimation(animationPacket.animation);
                break;
            case ProtocolInfo.TEXT_PACKET:
                TextPacket textPacket = (TextPacket) packet;
                //System.out.println("Recebendo");
                switch (textPacket.type){
                    case TextType.CHAT_MESSAGE:
                        if(textPacket.message.length() > 64){
                            TextPacket nextPacket = new TextPacket();
                            nextPacket.eid = -1;
                            nextPacket.name = "Servidor";
                            nextPacket.message = MessageConnection.HIGH_MESSAGE_ERROR;

                            this.dataPacket(nextPacket);
                            return;
                        }
                        MessageConnection.sendConsoleMessage(textPacket);
                        MessageConnection.sendTextPacket(textPacket, session.getServer().getOnlinePlayers().values());
                        break;
                }
                break;
        }
    }

    public void sendRuntime(){
        if(isSpawned)
            return;
        RuntimePlayerPacket pk = new RuntimePlayerPacket();
        pk.eid = this.getRuntimeId();
        this.dataPacket(pk);
    }

    public void sendAllPlayers(){
        for(Player player : session.getServer().getOnlinePlayers().values()){
            if(player.getRuntimeId() == this.getRuntimeId()) continue;
            AddEntityPacket packet = new AddEntityPacket();
            packet.eid = player.getRuntimeId();
            packet.name = player.username;
            this.dataPacket(packet);
            AddEntityPacket pk = new AddEntityPacket();
            pk.eid = this.getRuntimeId();
            pk.name = this.username;
            player.dataPacket(pk);
        }
    }

    public void updateMovement(){
        for(Player player : session.getServer().getOnlinePlayers().values()){
            if(player.getRuntimeId() == this.getRuntimeId()) continue;
            MoveEntityPacket packet = new MoveEntityPacket();
            packet.eid = this.getRuntimeId();
            packet.x = this.getX();
            packet.y = this.getY();

            player.dataPacket(packet);
        }
    }

    public void sendAnimation(int animation){
        for(Player player : session.getServer().getOnlinePlayers().values()){
            if(player.getRuntimeId() == this.getRuntimeId()) continue;
            AnimationPacket packet = new AnimationPacket();
            packet.eid = this.getRuntimeId();
            packet.animation = animation;

            player.dataPacket(packet);
        }
    }

    public long getRuntimeId(){
        return this.eid;
    }

    public String getUsername() {
        return username;
    }
}
