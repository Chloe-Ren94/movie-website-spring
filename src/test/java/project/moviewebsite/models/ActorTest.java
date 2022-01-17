package project.moviewebsite.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Actor class.
 */
class ActorTest {

    private Actor actor;
    private Country country;
    private Movie m1, m2;

    @BeforeEach
    void setUp() {
        country = new Country("Japan");
        actor = new Actor("Michiko Kichise", 1975, 2, 17, Gender.UNKNOWN, country);
        m1 = new Movie("Heidi", 111, 9.0, 2015, 12, 11, "", "");
        m2 = new Movie();
        m2.setTitle("Test");
        m2.setLength(100);
    }

    @Test
    void testGetName() {
        assertEquals("Michiko Kichise", actor.getName());
        actor.setName("Hiromi");
        assertEquals("Hiromi", actor.getName());
    }

    @Test
    void testGetBirthDate() {
        LocalDate birthDate = LocalDate.of(1975, 2, 17);
        assertEquals(birthDate, actor.getBirthDate());
        LocalDate newBirthDate = LocalDate.of(1986, 12, 5);
        actor.setBirthDate(newBirthDate);
        assertEquals(newBirthDate, actor.getBirthDate());
    }

    @Test
    void testGetGender() {
        assertEquals(Gender.UNKNOWN, actor.getGender());
        actor.setGender(Gender.FEMALE);
        assertEquals(Gender.FEMALE, actor.getGender());
    }

    @Test
    void testGetMovies() {
        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        actor.setMovies(movies);
        assertEquals(2, actor.getMovies().size());
        assertEquals(movies, actor.getMovies());
    }

    @Test
    void testAddMovie() {
        assertEquals(0, actor.getMovies().size());
        actor.addMovie(m2);
        assertEquals(1, actor.getMovies().size());
        actor.addMovie(m1);
        assertEquals(2, actor.getMovies().size());

        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        assertEquals(movies, actor.getMovies());
    }

    @Test
    void testRemoveMovie() {
        Set<Movie> movies = new HashSet<>();
        movies.add(m1);
        movies.add(m2);
        actor.setMovies(movies);
        assertEquals(2, actor.getMovies().size());

        actor.removeMovie(m2);
        assertEquals(1, actor.getMovies().size());
        actor.removeMovie(m1);
        assertEquals(0, actor.getMovies().size());
        assertTrue(actor.getMovies().isEmpty());
    }

    @Test
    void testGetCountry() {
        assertEquals(country, actor.getCountry());
        Country newCountry = new Country("Taiwan");
        actor.setCountry(newCountry);
        assertEquals(newCountry, actor.getCountry());
        assertNotEquals(country, actor.getCountry());
    }

    @Test
    void testEquals() {
        Actor sameActor = new Actor("Michiko Kichise", 1975, 2, 17, Gender.UNKNOWN, country);
        Actor differentActor = new Actor("Michiko Kichise", 1985, 2, 17, Gender.FEMALE, country);
        assertTrue(actor.equals(sameActor));
        assertEquals(actor.hashCode(), sameActor.hashCode());
        assertFalse(actor.equals(differentActor));
        assertNotEquals(actor.hashCode(), differentActor.hashCode());
    }

}