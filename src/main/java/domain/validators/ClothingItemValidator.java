package domain.validators;

import domain.ClothingItem;

/**
 * A specific Validator for entities of type ClothingItem.
 * @author catad
 *
 */
public class ClothingItemValidator implements Validator<ClothingItem> {

    /**
     * Checks all conditions in order to accurately validate an entity of type ClothingItem(the item type(name) and designer name should not be empty).
     *
     * @param entity
     * @throws ValidatorException
     *                          if any of the conditions is not met.
     */
    @Override
    public void validate(ClothingItem entity) throws ValidatorException {
        if(entity.getName().equals("") || entity.getName() == null)
            throw new ValidatorException("Invalid name. Can not be empty.");
        if(entity.getDesigner().equals("") || entity.getDesigner() == null)
            throw new ValidatorException("Invalid designer name. Can not be empty.");
    }
}
