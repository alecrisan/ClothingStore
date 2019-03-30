package repository;

import domain.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface PagingRepository<ID extends Serializable, T extends BaseEntity<ID>> extends Repository<ID, T> {

        Page<T> findAll(Pageable pageable);
        //TODO: any other methods are allowed...

}

