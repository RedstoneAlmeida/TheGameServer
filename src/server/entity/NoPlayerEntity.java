package server.entity;

import math.Vector2D;
import network.protocol.GenerateEntityPacket;
import server.Player;
import server.Server;

public class NoPlayerEntity extends Entity {

    public short EntityID = -1;
    public long eid;

    public long getRuntimeId() {
        return eid;
    }

    public short getEntityID() {
        return EntityID;
    }

    public void spawnTo(Player player){
        GenerateEntityPacket pk = new GenerateEntityPacket();
        pk.eid = this.getRuntimeId();
        pk.entityID = this.getEntityID();
        pk.vector2D = new Vector2D(this.getX(), this.getY());

        player.dataPacket(pk);
    }

    public void spawnToAll(){
        Server.getInstance().getOnlinePlayers().values().forEach(this::spawnTo);
    }
}
