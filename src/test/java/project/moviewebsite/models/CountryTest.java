package project.moviewebsite.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Country class.
 */
class CountryTest {

    private Country country;
    private Movie m1, m2;
    private Actor a1, a2;

    @BeforeEach
    void setUp() {
        country = new Country("Japan");

        m1 = new Movie("Heidi", 111, 9.0, 2015, 12, 11, "", "");
        m2 = new Movie();
        m2.setTitle("Test");
        m2.setLength(100);

        a1 = new Actor("Yuki Uchida", 1975, 11, 16, Gender.FEMALE, country);
        a2 = new Actor();
        a2.setName("Michiko Kichise");
        a2.setGender(Gender.UNKNOWN);
    }

    @Test
    void testGetName() {
        assertEquals("Japan", country.getName());
        country.setName("Iceland");
        assertEquals("Iceland", country.getName());
    }

    @Test
    void testGetMovies() {
        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        country.setMovies(movies);
        assertEquals(2, country.getMovies().size());
        assertEquals(movies, country.getMovies());
    }

    @Test
    void testAddMovie() {
        assertEquals(0, country.getMovies().size());
        country.addMovie(m2);
        assertEquals(1, country.getMovies().size());
        country.addMovie(m1);
        assertEquals(2, country.getMovies().size());

        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        assertEquals(movies, country.getMovies());
    }

    @Test
    void testRemoveMovie() {
        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        country.setMovies(movies);
        assertEquals(2, country.getMovies().size());

        country.removeMovie(m2);
        assertEquals(1, country.getMovies().size());
        country.removeMovie(m1);
        assertEquals(0, country.getMovies().size());
        assertTrue(country.getMovies().isEmpty());
    }

    @Test
    void testGetActors() {
        Set<Actor> actors = new HashSet<>();
        actors.add(a2);
        actors.add(a1);
        country.setActors(actors);
        assertEquals(2, country.getActors().size());
        assertEquals(actors, country.getActors());
    }

    @Test
    void testEquals() {
        Country sameCountry = new Country("Japan");
        Country differentCountry = new Country("France");
        assertTrue(country.equals(sameCountry));
        assertEquals(country.hashCode(), sameCountry.hashCode());
        assertFalse(country.equals(differentCountry));
        assertNotEquals(country.hashCode(), differentCountry.hashCode());
    }

}