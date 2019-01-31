package network.protocol;

/**
 * Created by ASUS on 13/01/2019.
 */
public class RuntimePlayerPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.GAME_START_PACKET;

    public long eid;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.eid = this.getLong();
    }

    @Override
    public void encode() {
        this.putLong(this.eid);
    }
}
