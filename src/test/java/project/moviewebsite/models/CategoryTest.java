package project.moviewebsite.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Category class.
 */
class CategoryTest {

    private Category category;
    private Movie m1, m2;

    @BeforeEach
    void setUp() {
        category = new Category("Thriller");
        m1 = new Movie("Secret", 101, 9.5, 2007, 7, 27, "", "");
        m2 = new Movie();
        m2.setTitle("The Sound of Music");
    }

    @Test
    void testGetName() {
        assertEquals("Thriller", category.getName());
        category.setName("Action");
        assertEquals("Action", category.getName());
    }

    @Test
    void testGetMovies() {
        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        category.setMovies(movies);
        assertEquals(2, category.getMovies().size());
        assertEquals(movies, category.getMovies());
    }

    @Test
    void testAddMovie() {
        category.addMovie(m1);
        assertEquals(1, category.getMovies().size());
        category.addMovie(m2);
        assertEquals(2, category.getMovies().size());

        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        assertEquals(movies, category.getMovies());
    }

    @Test
    void testRemoveMovie() {
        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        category.setMovies(movies);
        assertEquals(2, category.getMovies().size());

        category.removeMovie(m1);
        assertEquals(1, category.getMovies().size());
        category.removeMovie(m2);
        assertEquals(0, category.getMovies().size());
        assertTrue(category.getMovies().isEmpty());
    }

    @Test
    void testEquals() {
        Category sameCategory = new Category("Thriller");
        Category differentCategory = new Category("Fiction");
        assertTrue(category.equals(sameCategory));
        assertEquals(category.hashCode(), sameCategory.hashCode());
        assertFalse(category.equals(differentCategory));
        assertNotEquals(category.hashCode(), differentCategory.hashCode());
    }

}