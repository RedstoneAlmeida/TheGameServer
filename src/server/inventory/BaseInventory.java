package server.inventory;

import network.protocol.InventoryContentPacket;
import server.Player;
import server.inventory.material.ItemCard;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ASUS on 14/01/2019.
 */
public class BaseInventory implements Inventory {

    private Map<Integer, ItemCard> contents = new LinkedHashMap<>();
    private Player player;

    public BaseInventory(Player player){
        this.player = player;
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public Map<Integer, ItemCard> getContents() {
        return this.contents;
    }

    @Override
    public void setContents(Map<Integer, ItemCard> contents) {
        this.contents = contents;
    }

    @Override
    public ItemCard getItem(int index) {
        if(contents.containsKey(index))
            return contents.get(index);
        return null;
    }

    @Override
    public boolean setItem(int index, ItemCard card) {
        return this.setItem(index, card, false);
    }

    @Override
    public boolean setItem(int index, ItemCard card, boolean send) {
        try {
            if(contents.containsKey(index))
                contents.replace(index, card);
            else
                contents.put(index, card);
        } catch (Exception e) {
            return false;
        }


        if(send) {
            InventoryContentPacket packet = new InventoryContentPacket();
            ItemCard[] cards = new ItemCard[4];
            for(int key : contents.keySet())
                cards[key] = contents.get(key);
            packet.cards = cards;
            player.dataPacket(packet);
        }
        return true;
    }

    @Override
    public boolean contains(ItemCard card) {
        for(ItemCard item : contents.values()) {
            if (!item.equals(card)) continue;
            return true;
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }
}
