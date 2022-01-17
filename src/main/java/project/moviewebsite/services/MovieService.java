package project.moviewebsite.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.moviewebsite.models.Actor;
import project.moviewebsite.models.Category;
import project.moviewebsite.models.Country;
import project.moviewebsite.models.Movie;

import java.util.List;

/**
 * This interface represents all the operations for the Movie class.
 */
public interface MovieService extends CrudService<Movie> {

    /**
     * Return a Page object containing all movies.
     * @param pageable the Pageable object used to page
     * @return a Page object containing all movies
     */
    Page<Movie> findAll(Pageable pageable);

    /**
     * Delete the given actor from all movies.
     * @param actor the actor to be deleted
     */
    void deleteActor(Actor actor);

    /**
     * Delete the given country from all movies.
     * @param country the country to be deleted
     */
    void deleteCountry(Country country);

    /**
     * Delete the given category from all movies.
     * @param category the category to be deleted
     */
    void deleteCategory(Category category);

    /**
     * Find the movies whose title containing the given title.
     * @param title the title to be contained
     * @return a list of movies
     */
    List<Movie> findByTitleContainingIgnoreCase(String title);

    /**
     * Set the given file to the poster of the movie.
     * @param movieId the id of the movie
     * @param file the poster file of the movie
     */
    void savePoster(Long movieId, MultipartFile file);

}
