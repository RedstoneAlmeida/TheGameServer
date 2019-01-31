package network.protocol;

import utils.TextType;

/**
 * Created by ASUS on 16/01/2019.
 */
public class TextPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.TEXT_PACKET;

    public long eid;
    public String name;
    public String message;
    public int type = TextType.CHAT_MESSAGE;

    @Override
    public void decode() {
        this.eid = this.getLong();
        this.name = this.getString();
        this.message = this.getString();
        this.type = this.getInt();
    }

    @Override
    public void encode() {
        this.putLong(this.eid);
        this.putString(this.name);
        this.putString(this.message);
        this.putInt(this.type);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
