package network.protocol;

import math.Vector2D;
import server.entity.data.DataFlag;

public class GenerateEntityPacket extends DataPacket {

    public static byte NETWORK_ID = ProtocolInfo.GENERATE_ENTITY_PACKET;

    public long eid;
    public short entityID;

    public Vector2D vector2D;

    public String sprite = "NOT NEEDED, BUT IN MODS MAYBE";

    @Override
    public void decode() {
        this.eid = this.getLong();
        this.entityID = (short) this.getShort();
        this.vector2D = new Vector2D(this.getInt(), this.getInt());
        this.sprite = this.getString();
    }

    @Override
    public void encode() {
        this.putLong(this.eid);
        this.putShort(this.entityID);
        this.putInt(vector2D.getX());
        this.putInt(vector2D.getY());
        this.putString(this.sprite);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
