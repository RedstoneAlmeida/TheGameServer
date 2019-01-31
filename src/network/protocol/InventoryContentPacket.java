package network.protocol;

import server.inventory.material.ItemCard;

/**
 * Created by ASUS on 14/01/2019.
 */
public class InventoryContentPacket extends DataPacket {

    public ItemCard[] cards;

    @Override
    public void decode() {
        int length = this.getInt();
        cards = new ItemCard[length];
        for(int i = 0; i < length; i++){
            cards[i] = this.getItemPacket();
        }
    }

    @Override
    public void encode() {
        this.putInt(cards.length);
        for(int i = 0; i < cards.length; i++){
            this.putItemPacket(cards[i]);
        }
    }

    @Override
    public byte pid() {
        return 0;
    }
}
