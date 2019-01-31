package server.inventory.material;

import server.inventory.material.cards.BrokenSwordCard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 14/01/2019.
 */
public class ItemFactory {

    public Class[] list = null;

    public ItemFactory(){

    }

    private void registerItemCard(){
        if(list == null) {
            list = new Class[256];

            list[5] = BrokenSwordCard.class;
        }
    }

    public static String getItemName(int id){
        switch (id){
            case 5:
                return "Broken Sword";
            default:
                return "Unknown";
        }
    }
}
