package project.moviewebsite.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import project.moviewebsite.models.Actor;
import project.moviewebsite.models.Category;
import project.moviewebsite.models.Country;
import project.moviewebsite.models.Movie;
import project.moviewebsite.repositories.MovieRepository;

import java.io.IOException;
import java.util.*;

/**
 * This class represents a Spring Data JPA implementation of the MovieService interface.
 */
@Service
public class MovieServiceImpl extends CrudServiceImpl<Movie, MovieRepository> implements MovieService {

    /**
     * Construct a MovieServiceImpl with the given movieRepository.
     * @param movieRepository the movieRepository of the MovieServiceImpl
     */
    public MovieServiceImpl(MovieRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Override
    public void deleteActor(Actor actor) {
        for (Movie movie: findAll())
            movie.removeActor(actor);
    }

    @Override
    public void deleteCountry(Country country) {
        for (Movie movie: findAll())
            movie.removeCountry(country);
    }

    @Override
    public void deleteCategory(Category category) {
        for (Movie movie: findAll())
            movie.removeCategory(category);
    }

    @Override
    public List<Movie> findByTitleContainingIgnoreCase(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public void savePoster(Long movieId, MultipartFile file) {
        try {
            Movie movie = findById(movieId);
            movie.setPoster(file.getBytes());
            save(movie);
        } catch (IOException e) {
            System.out.println("Cannot load the poster");
        }
    }

}
