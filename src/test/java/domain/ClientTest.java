package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Class for running tests on Client entities.
 * @author catad
 */
public class ClientTest {

    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String NAME = "clientName";
    private static final String NEW_NAME = "clientName2";
    private static final int AGE = 23;
    private static final int NEW_AGE = 25;
    private static final String MEMBERSHIP = "Gold";
    private static final String NEW_MEMBERSHIP = "Silver";

    private Client client;

    /**
     * Sets up the entities to be tested.
     *
     * @throws Exception
     *                  in case entities are null.
     *
     */
    @Before
    public void setUp() throws Exception {
        client = new Client(NAME, AGE, MEMBERSHIP);
        client.setId(ID);
    }

    /**
     * Destroys the entities after testing them.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        client = null;
    }


    /**
     * Tests the getId() function for a Client - retrieves the {@code ID} and compares it to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, client.getId());
    }

    /**
     * Tests the setId() function for a Client - sets a new {@code ID} and compares the result of the getId() operation to the actual given {@code ID}.
     *
     * @throws Exception
     */
    @Test
    public void testSetId() throws Exception {
        client.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, client.getId());
    }

    /**
     * Tests the getName() function - Retrieves the name and compares it to the actual given name.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be equal", NAME, client.getName());
    }

    /**
     * Tests the setName() function - sets a new name and compares the result of the getName() operation to the given name.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSetName() throws Exception {
        client.setName(NEW_NAME);
        assertEquals("Names should be equal", NEW_NAME, client.getName());
    }

    /**
     * Tests the getAge() function - Retrieves the age and compares it to the actual given age.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetAge() throws Exception {
        assertEquals("Ages should be equal", AGE, client.getAge());
    }

    /**
     * Tests the setAge() function - sets a new age and compares the result of the getAge() operation to the given age.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSetAge() throws Exception {
        client.setAge(NEW_AGE);
        assertEquals("Ages should be equal", NEW_AGE, client.getAge());
    }

    /**
     * Tests the getMembershipType() function - Retrieves the membership type and compares it to the actual given membership type.
     *
     * @throws Exception
     *
     */
    @Test
    public void testGetMembershipType() throws Exception {
        assertEquals("Membership types should be equal", MEMBERSHIP, client.getMembershipType());
    }

    /**
     * Tests the setMembershipType() function - sets a new membership type and compares the result of the getMembershipType() operation to the given membership type.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSetMembershipType() throws Exception {
        client.setMembershipType(NEW_MEMBERSHIP);
        assertEquals("Membership types should be equal", NEW_MEMBERSHIP, client.getMembershipType());
    }

}