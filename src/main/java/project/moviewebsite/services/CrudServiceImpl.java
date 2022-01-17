package project.moviewebsite.services;

import org.springframework.data.repository.CrudRepository;
import project.moviewebsite.models.BaseEntity;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class represents a Spring Data JPA implementation of the CrudService interface.
 * @param <T> the type of objects
 * @param <R> the repository of the object
 */
public abstract class CrudServiceImpl<T extends BaseEntity, R extends CrudRepository<T, Long>> implements CrudService<T> {

    protected final R repository;

    /**
     * Construct a CrudServiceImpl with the given repository.
     * @param repository the repository of the CrudServiceImpl
     */
    protected CrudServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public Set<T> findAll() {
        Set<T> dataSet = new HashSet<>();
        repository.findAll().forEach(dataSet::add);
        return dataSet;
    }

    @Override
    public T findById(Long id) throws NoSuchElementException {
        return repository.findById(id).get();
    }

    @Override
    public T save(T data) {
        return repository.save(data);
    }

    @Override
    public void delete(T data) {
        repository.delete(data);
    }

}
