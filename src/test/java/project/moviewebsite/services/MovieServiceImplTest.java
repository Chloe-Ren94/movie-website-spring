package project.moviewebsite.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import project.moviewebsite.models.*;
import project.moviewebsite.repositories.MovieRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * This class tests the MovieServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    void findAll() {
        Movie movie = new Movie();
        Set<Movie> movieSet = new HashSet<>();
        movieSet.add(movie);

        when(movieRepository.findAll()).thenReturn(movieSet);
        Set<Movie> movies = movieService.findAll();

        assertEquals(1, movies.size());
        assertEquals(movieSet, movies);
        verify(movieRepository, times(1)).findAll();
        verify(movieRepository, never()).findById(anyLong());
    }

    @Test
    void findById() {
        Movie movie = new Movie();
        movie.setId(8L);
        Optional<Movie> movieOptional = Optional.of(movie);

        when(movieRepository.findById(anyLong())).thenReturn(movieOptional);
        Movie movieReturned = movieService.findById(8L);

        assertEquals(movieOptional.get(), movieReturned);
        verify(movieRepository, times(1)).findById(anyLong());
        verify(movieRepository, never()).findAll();
    }

    @Test
    void save() {
        Movie movie = new Movie();
        movie.setTitle("Soul");

        when(movieRepository.save(any())).thenReturn(movie);
        Movie movieReturned = movieService.save(movie);

        assertEquals(movie, movieReturned);
        verify(movieRepository, times(1)).save(any());
        verify(movieRepository, never()).findById(anyLong());
    }

    @Test
    void delete() {
        Movie movie = new Movie();
        movie.setTitle("Soul");

        movieService.delete(movie);

        verify(movieRepository, times(1)).delete(any());
    }

    @Test
    void deleteActor() {
        Actor actor = new Actor("Yuki", 1967, 11, 16, Gender.FEMALE, null);
        Movie movie = new Movie();
        movie.addActor(actor);
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);

        when(movieRepository.findAll()).thenReturn(movies);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        movieService.deleteActor(actor);

        assertFalse(movieService.findById(1L).getActors().contains(actor));
    }

    @Test
    void deleteCountry() {
        Country country = new Country("England");
        Movie movie = new Movie();
        movie.addCountry(country);
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);

        when(movieRepository.findAll()).thenReturn(movies);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        movieService.deleteCountry(country);

        assertFalse(movieService.findById(1L).getCountries().contains(country));
    }

    @Test
    void deleteCategory() {
        Category category = new Category("Thriller");
        Movie movie = new Movie();
        movie.addCategory(category);
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);

        when(movieRepository.findAll()).thenReturn(movies);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        movieService.deleteCategory(category);

        assertFalse(movieService.findById(1L).getCategories().contains(category));
    }

    @Test
    void findByTitleContainingIgnoreCase() {
        Movie movie = new Movie();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        when(movieRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(movieList);
        List<Movie> movies = movieService.findByTitleContainingIgnoreCase(anyString());

        assertEquals(1, movies.size());
        assertEquals(movieList, movies);
        verify(movieRepository, times(1)).findByTitleContainingIgnoreCase(anyString());
        verify(movieRepository, never()).findById(anyLong());
    }

    @Test
    void savePoster() throws Exception {
        Movie movie = new Movie();
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "Movie test".getBytes());

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(movieRepository.save(any())).thenReturn(movie);

        movieService.savePoster(anyLong(), multipartFile);
        assertEquals(multipartFile.getBytes(), movie.getPoster());
    }

}