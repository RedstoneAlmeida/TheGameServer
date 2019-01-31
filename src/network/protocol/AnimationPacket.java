package network.protocol;

/**
 * Created by ASUS on 14/01/2019.
 */
public class AnimationPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.ANIMATION_PACKET;

    public long eid;
    public int animation;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.eid = this.getLong();
        this.animation = this.getInt();
    }

    @Override
    public void encode() {
        this.putLong(this.eid);
        this.putInt(this.animation);
    }
}
