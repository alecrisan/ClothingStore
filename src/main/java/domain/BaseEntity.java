package domain;

/**
 * Class for entities of a specific type.
 * @author ale
 * @param <ID>
 *
 */
public class BaseEntity<ID> {

    private ID id;

    /**
     * Returns the {@code ID} of an entity.
     *
     * @return a {@code ID} of an entity
     *
     */
    public ID getId()
    {
        return id;
    }

    /**
     * Sets the {@code id} of an entity.
     *
     * @param id
     *
     */
    public void setId(ID id)
    {
        this.id = id;
    }

    /**
     * Returns the entity in a readable form.
     *
     * @return a String
     *
     */
    @Override
    public String toString()
    {
        return "BaseEntity(" + "id = " + id + ')';
    }

    public String toStringFile() { return ""; }
}
