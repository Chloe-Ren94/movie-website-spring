package project.moviewebsite.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.moviewebsite.models.Actor;
import project.moviewebsite.models.Country;

import java.util.List;

/**
 * This interface represents all the operations for the Actor class.
 */
public interface ActorService extends CrudService<Actor> {

    /**
     * Return a Page object containing all actors.
     * @param pageable the Pageable object used to page
     * @return a Page object containing all actors
     */
    Page<Actor> findAll(Pageable pageable);

    /**
     * Delete the given country from all actors.
     * @param country the country to be deleted
     */
    void deleteCountry(Country country);

    /**
     * Find the actors whose name containing the given name.
     * @param name the name to be contained
     * @return a list of actors
     */
    List<Actor> findByNameContainingIgnoreCase(String name);

    /**
     * Set the given file to the photo of the actor.
     * @param actorId the id of the actor
     * @param file the photo file of the actor
     */
    void savePhoto(Long actorId, MultipartFile file);

}
