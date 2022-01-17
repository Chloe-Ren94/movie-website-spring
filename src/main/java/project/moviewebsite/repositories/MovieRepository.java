package project.moviewebsite.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import project.moviewebsite.models.Movie;

import java.util.List;

/**
 * This interface represents a repository for the Movie class.
 */
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    /**
     * Find the movies whose title containing the given title.
     * @param title the title to be contained
     * @return a list of movies
     */
    List<Movie> findByTitleContainingIgnoreCase(String title);

    /**
     * Return a Page object containing all movies.
     * @param pageable the Pageable object used to page
     * @return a Page object containing all movies
     */
    Page<Movie> findAll(Pageable pageable);

}
