package domain.validators;
import domain.Transaction;

/**
 * A specific Validator for entities of type Transaction.
 * @author catad
 *
 */

public class TransactionValidator implements Validator<Transaction>{
    /**
     * Checks all conditions in order to accurately validate an entity of type Transaction(the list must not be empty, the price must not be 0, the Client must exist, and the date should be real)
     * @param entity
     * @throws ValidatorException
     *                          if any of the conditions is not met.
     */
    @Override
    public void validate(Transaction entity) throws ValidatorException {
        if(entity.getClient() == null)
            throw new ValidatorException("Client can't be null");
        if(entity.getItems().isEmpty())
            throw new ValidatorException("Item list can't be empty");
        if(entity.getTotalPrice() == 0)
            throw new ValidatorException("Price must not be zero(0)");
        if(Integer.parseInt(entity.getDate().split("/")[0]) > 31 || Integer.parseInt(entity.getDate().split("/")[0]) < 1 || Integer.parseInt(entity.getDate().split("/")[1]) > 12 || Integer.parseInt(entity.getDate().split("/")[1]) < 1 || Integer.parseInt(entity.getDate().split("/")[2]) > 2019)
            throw new ValidatorException("Date is not valid");
    }
}
