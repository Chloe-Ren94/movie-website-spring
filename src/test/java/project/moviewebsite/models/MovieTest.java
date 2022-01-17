package project.moviewebsite.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Movie class.
 */
class MovieTest {

    Movie movie;
    Category c1, c2, c3;
    Actor a1, a2;
    Country country1, country2;

    @BeforeEach
    void setUp() {
        String description = "Ye Xiang Lun, a talented piano player is a new student at the prestigious " +
                             "Tamkang School. On his first day, he meets Lu Xiao Yu, a pretty girl playing " +
                             "a mysterious piece of music.";
        String review = "Fantastic movie! Fantastic music!";
        movie = new Movie("Secret", 101, 9.5, 2007, 7, 27, description, review);

        c1 = new Category("Drama");
        c2 = new Category("Fantasy");
        c3 = new Category("Music");

        country1 = new Country("Hong Kong");
        country2 = new Country("Taiwan");

        a1 = new Actor("Jay Chou", 1979, 1, 18, Gender.MALE, country2);
        a2 = new Actor("Lunmei Gwei", 1983, 12, 25, Gender.FEMALE, country2);
    }

    @Test
    void testGetTitle() {
        assertEquals("Secret", movie.getTitle());
        movie.setTitle("New Movie");
        assertEquals("New Movie", movie.getTitle());
    }

    @Test
    void testGetLength() {
        assertEquals(101, movie.getLength());
        movie.setLength(222);
        assertEquals(222, movie.getLength());
    }

    @Test
    void testGetRating() {
        assertEquals(9.5, movie.getRating(), 0.001);
        movie.setRating(10.0);
        assertEquals(10.0, movie.getRating(), 0.001);
    }

    @Test
    void testGetReleaseDate() {
        assertEquals(LocalDate.of(2007, 7, 27), movie.getReleaseDate());
        LocalDate newDate = LocalDate.of(2017, 11, 16);
        movie.setReleaseDate(newDate);
        assertEquals(newDate, movie.getReleaseDate());
    }

    @Test
    void testGetUrl() {
        String url = "https://www.imdb.com/title/tt1037850/?ref_=nv_sr_srsg_0";
        movie.setUrl(url);
        assertEquals(url, movie.getUrl());
    }

    @Test
    void testGetCategories() {
        assertTrue(movie.getCategories().isEmpty());
        movie.addCategory(c1);
        assertEquals(1, movie.getCategories().size());
        movie.addCategory(c2);
        movie.addCategory(c3);
        assertEquals(3, movie.getCategories().size());

        Set<Category> categories = new HashSet<>();
        categories.add(c3);
        categories.add(c2);
        categories.add(c1);
        assertEquals(categories, movie.getCategories());

        movie.removeCategory(c3);
        assertEquals(2, movie.getCategories().size());
        movie.removeCategory(c1);
        movie.removeCategory(c2);
        assertTrue(movie.getCategories().isEmpty());
    }

    @Test
    void testGetActors() {
        assertTrue(movie.getActors().isEmpty());
        movie.addActor(a1);
        assertEquals(1, movie.getActors().size());
        movie.addActor(a2);
        assertEquals(2, movie.getActors().size());

        Set<Actor> actors = new HashSet<>();
        actors.add(a2);
        actors.add(a1);
        assertEquals(actors, movie.getActors());

        movie.removeActor(a2);
        assertEquals(1, movie.getActors().size());
        movie.removeActor(a1);
        assertTrue(movie.getActors().isEmpty());
    }

    @Test
    void testGetCountries() {
        assertTrue(movie.getCountries().isEmpty());
        movie.addCountry(country1);
        assertEquals(1, movie.getCountries().size());
        movie.addCountry(country2);
        assertEquals(2, movie.getCountries().size());

        Set<Country> countries = new HashSet<>();
        countries.add(country1);
        countries.add(country2);
        assertEquals(countries, movie.getCountries());

        movie.removeCountry(country2);
        assertEquals(1, movie.getCountries().size());
        movie.removeCountry(country1);
        assertTrue(movie.getCountries().isEmpty());
    }

    @Test
    void testGetDescription() {
        String description = "Ye Xiang Lun, a talented piano player is a new student at the prestigious " +
                "Tamkang School. On his first day, he meets Lu Xiao Yu, a pretty girl playing " +
                "a mysterious piece of music.";
        assertEquals(description, movie.getDescription());
        movie.setDescription("A fantastic love story.");
        assertEquals("A fantastic love story.", movie.getDescription());
    }

    @Test
    void testGetReview() {
        assertEquals("Fantastic movie! Fantastic music!", movie.getReview());
        movie.setReview("Great!!!");
        assertEquals("Great!!!", movie.getReview());
    }

    @Test
    void testGetCategoryName() {
        movie.addCategory(c1);
        movie.addCategory(c2);
        movie.addCategory(c3);
        assertEquals("Drama/Music/Fantasy", movie.getCategoryName());
    }

    @Test
    void testGetCountryName() {
        movie.addCountry(country1);
        movie.addCountry(country2);
        assertEquals("Hong Kong/Taiwan", movie.getCountryName());
    }

    @Test
    void testEquals() {
        Movie sameMovie = new Movie("Secret", 101, 9.5, 2007, 7, 27, "", "");
        Movie differentMovie = new Movie("Secret", 158, 8.0, 2015, 1, 12, "", "");
        assertTrue(movie.equals(sameMovie));
        assertEquals(movie.hashCode(), sameMovie.hashCode());
        assertFalse(movie.equals(differentMovie));
        assertNotEquals(movie.hashCode(), differentMovie.hashCode());
    }

}