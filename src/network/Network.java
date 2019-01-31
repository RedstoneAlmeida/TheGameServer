package network;

import network.protocol.*;

/**
 * Created by ASUS on 12/01/2019.
 */
public class Network {

    public Network(){
        this.registerPackets();
    }

    @SuppressWarnings("unchecked")
    private Class<? extends DataPacket>[] packetPool = new Class[256];

    public DataPacket getPacket(byte id) {
        Class<? extends DataPacket> clazz = this.packetPool[id & 0xff];
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {

            }
        }
        return null;
    }

    public void registerPacket(byte id, Class<? extends DataPacket> clazz) {
        this.packetPool[id & 0xff] = clazz;
    }

    public void registerPackets(){
        this.registerPacket(ProtocolInfo.HANDLER_PACKET, HandlerPacket.class);
        this.registerPacket(ProtocolInfo.GAME_START_PACKET, GameStartPacket.class);
        this.registerPacket(ProtocolInfo.RUNTIME_PLAYER_PACKET, RuntimePlayerPacket.class);
        this.registerPacket(ProtocolInfo.ADD_ENTITY_PACKET, AddEntityPacket.class);
        this.registerPacket(ProtocolInfo.MOVE_PLAYER_PACKET, MoveEntityPacket.class);
        this.registerPacket(ProtocolInfo.ANIMATION_PACKET, AnimationPacket.class);
        this.registerPacket(ProtocolInfo.TEXT_PACKET, TextPacket.class);
    }

}
