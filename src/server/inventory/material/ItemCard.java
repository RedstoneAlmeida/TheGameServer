package server.inventory.material;

/**
 * Created by ASUS on 14/01/2019.
 */
public class ItemCard {

    private int id;
    private int count;
    private String name;

    public ItemCard(int id, int count){
        this.id = id;
        this.count = count;
        this.name = ItemFactory.getItemName(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public boolean equals(ItemCard card){
        if(this.id == card.id
                && this.name.equals(card.name) && this.count == card.count)
            return true;
        return false;
    }
}
