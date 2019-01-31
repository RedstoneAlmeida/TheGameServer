package server.inventory;

import server.inventory.material.ItemCard;

import java.util.Map;

/**
 * Created by ASUS on 14/01/2019.
 */
public interface Inventory {

    int MAX_STACK = 3;

    int getSize();

    Map<Integer, ItemCard> getContents();

    void setContents(Map<Integer, ItemCard> contents);

    ItemCard getItem(int index);

    default boolean setItem(int index, ItemCard card){
        return setItem(index, card, true);
    }

    boolean setItem(int index, ItemCard card, boolean send);

    boolean contains(ItemCard card);

}
