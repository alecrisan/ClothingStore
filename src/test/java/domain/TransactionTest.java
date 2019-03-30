package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Class for running tests on Transaction entities.
 * @author ale
 */
public class TransactionTest {

    private static final String DATE = "22/02/2019";
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String NAME = "clientName";
    private static final int AGE = 23;
    private static final String MEMBERSHIP = "Gold";
    private static final int PRICE = 0;
    private static final int NEW_PRICE = 2;

    private Transaction item;
    private Client c;
    private ArrayList<ClothingItem> itemsBought;

    /**
     * Sets up the entities to be tested.
     *
     * @throws Exception
     *                  in case entities are null.
     *
     */
    @Before
    public void setUp() throws Exception {
        itemsBought = new ArrayList<ClothingItem>();
        c = new Client(NAME, AGE, MEMBERSHIP);
        c.setId(ID);
        item = new Transaction(c, DATE);
        item.setId(ID);
    }

    /**
     * Destroys the entities after testing them.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        item = null;
    }

    /**
     * Tests the getId() function for a Transaction - retrieves the {@code ID} and compares it to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, item.getId());
    }

    /**
     * Tests the setId() function for a Transaction - sets a new {@code ID} and compares the result of the getId() operation to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testSetId() throws Exception {
        item.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, item.getId());
    }

    /**
     * Tests the getClient() function - Retrieves the client and compares it to the actual given client.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetClient() throws Exception {
        assertEquals("Clients should be equal", c, item.getClient());
    }

    /**
     * Tests the getItems() function - Retrieves the list of items and compares it to the actual given list of items.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetItems() throws Exception {
        assertEquals("Items should be equal", itemsBought, item.getItems());
    }

    /**
     * Tests the getTotalPrice() function - Retrieves the price and compares it to the actual given price.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetTotalPrice() throws Exception {
        assertEquals("Prices should be equal", PRICE, item.getTotalPrice());
    }

    /**
     * Tests the getDate() function - Retrieves the date and compares it to the actual given date.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetDate() throws Exception {
        assertEquals("Dates should be equal", DATE, item.getDate());
    }

    /**
     * Tests the setTotal() function for a Transaction - sets a new {@code ID} and compares the result of the getId() operation to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testSetTotal() throws Exception {
        item.setTotal(NEW_PRICE);
        assertEquals("Prices should be equal", NEW_PRICE, item.getTotalPrice());
    }

    /**
     * Tests the addItemToCart() function for a Transaction - adds a new item into the cart and compares it to the actual list of items.
     *
     * @throws Exception
     */
    @Test
    public void testAddItem() throws Exception {
        ClothingItem i = new ClothingItem("skirt", "Paul", 20);
        itemsBought.add(i);
        item.addItemToCart(i);
        assertEquals("Item should be in cart", itemsBought, item.getItems());


    }

}