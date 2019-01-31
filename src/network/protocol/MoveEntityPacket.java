package network.protocol;

/**
 * Created by ASUS on 13/01/2019.
 */
public class MoveEntityPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.MOVE_PLAYER_PACKET;

    public long eid;
    public int x;
    public int y;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.eid = this.getLong();
        this.x = this.getInt();
        this.y = this.getInt();
    }

    @Override
    public void encode() {
        this.putLong(this.eid);
        this.putInt(this.x);
        this.putInt(this.y);
    }
}
