package repository;

import domain.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for generic CRUD operations on a repository for a specific type.
 *
 * @param <ID>
 * @param <T>
 */
public class inMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    protected Map<ID, T> entities;

    protected Validator<T> validator;

    public inMemoryRepository(){}
    /**
     * Constructor for a repository.
     *
     * @param validator
     *
     */
    public inMemoryRepository(Validator<T> validator)
    {
        this.validator = validator;
        entities = new HashMap<>();
    }

    /**
     * Returns the hashmap that stores all entities.
     *
     * @return Map
     *
     */
    public Map<ID, T> getEntities(){
        return this.entities;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }

        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll()
    {
        Set<T> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());

        return allEntities;
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id)
    {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
