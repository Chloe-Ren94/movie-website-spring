package project.moviewebsite.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import project.moviewebsite.models.Actor;


import java.util.List;

/**
 * This interface represents a repository for the Actor class.
 */
public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {

    /**
     * Find the actors whose name containing the given name.
     * @param name the name to be contained
     * @return a list of actors
     */
    List<Actor> findByNameContainingIgnoreCase(String name);

    /**
     * Return a Page object containing all actors.
     * @param pageable the Pageable object used to page
     * @return a Page object containing all actors
     */
    Page<Actor> findAll(Pageable pageable);

}
