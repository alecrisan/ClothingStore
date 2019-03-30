package domain;

/**
 * Class for a specific type of entity(ClothingItem).
 * @author ale
 *
 */
public class ClothingItem extends BaseEntity<Long>{

    private String name;

    private String designer;

    private int price;

    /**
     * Default constructor for an entity of type ClothingItem.
     */
    public ClothingItem() {
    }

    /**
     * Constructor for an entity of type ClothingItem
     *
     * @param n(String) the item name
     * @param d(String) the designer name
     * @param p(int) the item's price
     *
     */
    public ClothingItem(String n, String d, int p) {
        this.name = n;
        this.designer = d;
        this.price = p;
    }

    /**
     * Gets the item name.
     *
     * @return String
     *
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the name of the designer of the item.
     *
     * @return String
     *
     */
    public String getDesigner()
    {
        return this.designer;
    }

    /**
     * Sets a new item name.
     *
     * @param n(String)
     *
     */
    public void setName(String n)
    {
        this.name = n;
    }

    /**
     * Sets a new name for the designer.
     *
     * @param d(String)
     *
     */
    public void setDesigner(String d)
    {
        this.designer = d;
    }

    /**
     * Returns the price of the item
     *
     * @return int
     */
    public int getPrice() { return this.price; }


    /**
     * Sets a new price for the item
     *
     * @param p(int)
     *
     */
    public void setPrice(int p){ this.price = p; }

    /**
     * Returns the info of a ClothingItem in a readable form.
     *
     * @return String
     *
     */
    @Override
    public String toString()
    {
        return "Clothing Item - Name: " + this.name + ", Designer: " + this.designer + ", Price: " + ((Integer) this.price).toString() + '\n';
        // + super.toString() ??
    }

    @Override
    public String toStringFile(){
        return this.getId() + "," + this.getName() + "," + this.getDesigner() + "," + this.getPrice() + '\n';
    }
}
