package network.protocol;

/**
 * Created by ASUS on 13/01/2019.
 */
public class AddEntityPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.ADD_ENTITY_PACKET;

    public long eid;
    public String name;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.eid = this.getLong();
        this.name = this.getString();
    }

    @Override
    public void encode() {
        this.putLong(eid);
        this.putString(name);
    }
}
