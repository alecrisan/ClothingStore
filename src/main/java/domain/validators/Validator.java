package domain.validators;

/**
 * Interface for a validator of a specific type.
 *
 * @author catad
 *
 */
public interface Validator<T> {

    /**
     * Validate the entity.
     *
     * @param entity
     * @throws ValidatorException
     *                      if conditions for validation are not met.
     *
     */
    void validate(T entity) throws ValidatorException;
}
