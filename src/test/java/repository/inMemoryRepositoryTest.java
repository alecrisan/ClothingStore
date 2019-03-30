package repository;

import domain.Client;
import domain.validators.ClientValidator;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;

/**
 * Class for testing repository functions.
 * @author catad
 */
public class inMemoryRepositoryTest {

    private static final Long ID1 = new Long(1);
    private static final Long ID2 = new Long(2);
    Validator<Client> validator = new ClientValidator();
    Repository<Long, Client> repository = new inMemoryRepository<>(validator);
    Client c1 = new Client("Mary", 20, "Gold");
    Client c2 = new Client("John", 25, "Silver");

    /**
     * Sets up the environment for future tests to be possible.
     */
    @Before
    public void setUp(){

        c1.setId(ID1);
        c2.setId(ID2);
        this.repository.save(c1);
        this.repository.save(c2);
    }

    /**
     * Tests the findOne() function - checks if a certain entity is present.
     *
     * @throws Exception
     *
     */
    @Test
    public void testFindOne() throws Exception {
        //assertEquals("Entities should be equal.", java.util.Optional.of(c1), this.repository.findOne(c1.getId()));
        assertTrue("Entity should be present.", this.repository.findOne(c1.getId()).isPresent());
    }


    /**
     * Tests the findAll() function - checks whether the lengths of the 2 sets are equal.
     *
     * @throws Exception
     *
     */
    @Test
    public void testFindAll() throws Exception {
        assertEquals("Sets should be the same length.",2, StreamSupport.stream(repository.findAll().spliterator(), false).count());
    }


    /**
     * Tests the save() function - adds a new entity and checks the equality of the expected length of the repo with the actual length.
     *
     * @throws Exception
     *
     */
    @Test
    public void testSave() throws Exception {
        Client c3 = new Client("Anna", 21, "Platinum");
        final Long ID3 = new Long(3);
        c3.setId(ID3);
        this.repository.save(c3);
        assertEquals("Repository should contain the new entity.", 3, StreamSupport.stream(repository.findAll().spliterator(), false).count());
    }



    /**
    * Tests the hypothesis where the save() function throws an exception.
    *
    * @throws Exception
    *
    */
    @Test(expected = ValidatorException.class)
    public void testSaveException() throws Exception {
        Client c4 = new Client("Henry", 13, "Platinum");
        Long ID4 = new Long(4);
        c4.setId(ID4);
        this.repository.save(c4);
    }


    /**
     * Tests the delete() function - deletes an entity and checks the equality of the expected length of the repo with the actual length.
     *
     * @throws Exception
     *
     */
    @Test
    public void testDelete() throws Exception {
        repository.delete(c2.getId());
        assertEquals("Entity should be deleted.", 1, StreamSupport.stream(repository.findAll().spliterator(), false).count());
    }


    /**
    * Tests the update() function -
    *
    * @throws Exception
    *
    */
    @Test
    public void testUpdate() throws Exception {
        Client clientUpdate = new Client("George", 25, "Black");
        clientUpdate.setId(c1.getId());
        repository.update(clientUpdate);
        assertEquals("Name should be changed", "George", repository.findOne(c1.getId()).get().getName());
        assertEquals("Age should be changed", 25, repository.findOne(c1.getId()).get().getAge());
        assertEquals("Membership type should be changed", "Black", repository.findOne(c1.getId()).get().getMembershipType());
        assertEquals("The number of elements should remain the same", 2, StreamSupport.stream(repository.findAll().spliterator(), false).count());
    }

    /**
    * Tests the hypothesis where the update() function throws an exception.
    *
    * @throws Exception
    *
    */
    @Test(expected = ValidatorException.class)
    public void testUpdateException() throws Exception {
        Client clientUpdateBad = new Client("Joe", 13, "Platinum");
        clientUpdateBad.setId(c2.getId());
        this.repository.update(clientUpdateBad);
    }

}
