package domain.validators;

/**
 * Class for exceptions regarding exceptions related to the validation of specific entities.
 * @author ale
 *
 */
public class ValidatorException extends StoreException{

    public ValidatorException(String message)
    {
        super(message);
    }

    public ValidatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ValidatorException(Throwable cause)
    {
        super(cause);
    }
}
