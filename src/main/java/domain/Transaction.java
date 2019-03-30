package domain;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A class for a specific entity type(Transaction)
 * @author catad
 *
 */

public class Transaction extends BaseEntity<Long>{

    ArrayList<ClothingItem> itemsBought;
    Client client;
    int totalPrice;
    String date;

    public Transaction(){}

    /**
     * Constructor for an entity of type Transaction.
     *
     * @param c(Client) the client
     * @param d(String) the date
     *
     */

    public Transaction(Client c, String d){
        itemsBought = new ArrayList<ClothingItem>();
        client = c;
        totalPrice = 0;
        date = d;
    }

    /**
     * Gets the Client.
     *
     * @return Client
     *
     */

    public Client getClient(){
        return this.client;
    }

    /**
     * Gets the list of items.
     *
     * @return List<ClothingItem></ClothingItem>
     *
     */

    public List<ClothingItem> getItems(){
        return this.itemsBought;
    }

    /**
     * Gets the total price of the items.
     *
     * @return int
     *
     */

    public int getTotalPrice(){
        return this.totalPrice;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return String
     *
     */

    public String getDate(){
        return this.date;
    }

    /**
     * Sets a new total.
     *
     * @param t(int)
     *
     */

    public void setTotal(int t){
        this.totalPrice = t;
    }

    /**
     * Sets a new total.
     *
     * @param c(Client)
     *
     */
    public void setClient(Client c) { this.client = c;}

    /**
     * Sets a new total.
     *
     * @param d(String)
     *
     */
    public void setDate(String d) { this.date = d;}

    public void setItemsBought(Integer[] l)
    {
        ArrayList<ClothingItem> items = new ArrayList<>();
        for (int i: l) {
            ClothingItem item = new ClothingItem();
            item.setId(Long.parseLong(String.valueOf(i)));
            items.add(item);
        }

        itemsBought = items;
    }

    /**
     * Adds a new item to the shopping cart(the list of bought items)
     *
     * @param i(ClothingItem)
     *
     */
    public void addItemToCart(ClothingItem i){
        this.itemsBought.add(i);
        int t = this.totalPrice + i.getPrice();
        this.setTotal(t);
    }

    public void updatePrice(){
        int total = StreamSupport.stream(this.itemsBought.spliterator(), false).mapToInt(o -> o.getPrice()).sum();
        this.setTotal(total);
    }

    /**
     * Returns the info of a Transaction in a readable form.
     *
     * @return String
     *
     */
    @Override
    public String toString(){
        String items = "";
        for (ClothingItem item : this.getItems()) {
            items = items + "  " + item.toString();
        }
        return "Client " + this.getClient().toString() + " bought items: " + items + " on " + this.getDate() + " for the total price of " + ((Integer)this.getTotalPrice()).toString();
    }

    @Override
    public String toStringFile(){
        return this.getClient().toStringFile() + "," + this.getId() + "," + this.getDate() + '\n';
    }

}
