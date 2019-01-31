package network.protocol;

/**
 * Created by ASUS on 13/01/2019.
 */
public class GameStartPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.GAME_START_PACKET;

    public String name;

    @Override
    public void decode() {
        this.name = this.getString();
    }

    @Override
    public void encode() {
        this.putString(this.name);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
