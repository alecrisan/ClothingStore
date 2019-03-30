package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Class for running tests on ClothingItem entities.
 * @author ale
 */
public class ClothingItemTest {

    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String NAME = "itemName";
    private static final String NEW_NAME = "itemName2";
    private static final String DESIGNER = "designer";
    private static final String NEW_DESIGNER = "designer2";
    private static final int PRICE = 10;
    private static final int NEW_PRICE = 20;

    private ClothingItem item;

    /**
     * Sets up the entities to be tested.
     *
     * @throws Exception
     *                  in case entities are null.
     *
     */
    @Before
    public void setUp() throws Exception {
        item = new ClothingItem(NAME, DESIGNER, PRICE);
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
     * Tests the getId() function for a ClothingItem - retrieves the {@code ID} and compares it to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, item.getId());
    }

    /**
     * Tests the setId() function for a ClothingItem - sets a new {@code ID} and compares the result of the getId() operation to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testSetId() throws Exception {
        item.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, item.getId());
    }

    /**
     * Tests the getName() function - Retrieves the name and compares it to the actual given name.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be equal", NAME, item.getName());
    }

    /**
     * Tests the setName() function - sets a new name and compares the result of the getName() operation to the given name.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSetName() throws Exception {
        item.setName(NEW_NAME);
        assertEquals("Names should be equal", NEW_NAME, item.getName());
    }

    /**
     * Tests the getDesigner() function - Retrieves the designer name and compares it to the actual given designer name.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetDesigner() throws Exception {
        assertEquals("Designer names should be equal", DESIGNER, item.getDesigner());
    }

    /**
     * Tests the setDesigner() function - sets a new designer name and compares the result of the getDesigner() operation to the given designer name.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSetDesigner() throws Exception {
        item.setDesigner(NEW_DESIGNER);
        assertEquals("Designer names should be equal", NEW_DESIGNER, item.getDesigner());
    }

    /**
     * Tests the getPrice() function - Retrieves the price and compares it to the actual given price.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetPrice() throws Exception {
        assertEquals("Prices should be equal", PRICE, item.getPrice());
    }

    /**
     * Tests the setPrice() function - sets a new price and compares the result of the getPrice() operation to the given Price.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSetPrice() throws Exception {
        item.setPrice(NEW_PRICE);
        assertEquals("Prices should be equal", NEW_PRICE, item.getPrice());
    }
}