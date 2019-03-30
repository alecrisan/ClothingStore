package domain.validators;

import domain.Client;

/**
 * A specific Validator for entities of type Client.
 * @author catad
 *
 */
public class ClientValidator implements Validator<Client>{

    /**
     * Checks all conditions in order to accurately validate an entity of type Client(their names should not be empty, their age should be legal/objectively possible, their membership type must be valid(it exists in the system)).
     *
     * @param entity
     * @throws ValidatorException
     *                          if any of the conditions is not met.
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getName().equals("") || entity.getName() == null)
            throw new ValidatorException("Invalid name. Can not be empty.");
        if(entity.getAge()  < 14 || entity.getAge() > 110)
            throw new ValidatorException("Age not valid.");
        if(!entity.getMembershipType().equals("Gold") && !entity.getMembershipType().equals("Silver") && !entity.getMembershipType().equals("Platinum") && !entity.getMembershipType().equals("Black") && !entity.getMembershipType().equals("White"))
            throw new ValidatorException("Membership type not valid. Must be Gold/Silver/Platinum/Black/White");


    }
}
