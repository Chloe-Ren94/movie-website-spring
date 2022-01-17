package project.moviewebsite.services;

import java.util.Set;

/**
 * This interface represents the common CRUD operations for all services.
 * @param <T> the type in the service object
 */
public interface CrudService<T> {

    /**
     * Find all objects.
     * @return a set of all objects
     */
    Set<T> findAll();

    /**
     * Find the object with the given id
     * @param id the id of the object.
     * @return the object with the given id
     */
    T findById(Long id);

    /**
     * Save the object to the database.
     * @param data the object to be saved
     * @return the saved object
     */
    T save(T data);

    /**
     * Delete the given object from the database.
     * @param data the object to be deleted
     */
    void delete(T data);

}
