package project.moviewebsite.models;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents a movie.
 */
@Entity
public class Movie extends BaseEntity {

    @NotBlank
    private String title;

    @Min(1)
    private Integer length;

    @DecimalMax("10.0")
    @DecimalMin("0.0")
    @Digits(integer = 2, fraction = 1)
    private Double rating;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @URL
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_category", joinColumns = @JoinColumn(name = "movie_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_country", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Set<Country> countries = new HashSet<>();

    @Lob
    private String description;

    @Lob
    private String review;

    @Lob
    private byte[] poster;

    /**
     * Construct a movie with no argument. All fields will be set to the default value.
     */
    public Movie() {}

    /**
     * Construct a movie with the given title, length, rating, released year, month and day,
     * description, and review.
     * @param title the title of the movie
     * @param length the length of the movie
     * @param rating the rating of the movie
     * @param year the released year of the movie
     * @param month the released month of the movie
     * @param day the released day of the movie
     * @param description the description of the movie
     * @param review the review of the movie
     * @throws IllegalArgumentException when the released year, month and day are invalid
     */
    public Movie(String title, Integer length, Double rating, int year, int month, int day, String description, String review) throws IllegalArgumentException {
        try {
            LocalDate releaseDate = LocalDate.of(year, month, day);
            this.title = title;
            this.length = length;
            this.rating = rating;
            this.releaseDate = releaseDate;
            this.description = description;
            this.review = review;
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid release date");
        }
    }

    /**
     * Return the title of the movie.
     * @return the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the movie to the given title.
     * @param title the title to be set to the movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the length of the movie.
     * @return the length of the movie
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Set the movie to the given length.
     * @param length the length to be set to the movie
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * Return the rating of the movie.
     * @return the rating of the movie
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Set the movie to the given rating.
     * @param rating the rating to be set to the movie
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Return the release date of the movie.
     * @return the release date of the movie
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set the release date to the given rating.
     * @param releaseDate the release date to be set to the movie
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Return the url of the movie.
     * @return the url of the movie
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url to the given rating.
     * @param url the url to be set to the movie
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return the categories of the movie.
     * @return the categories of the movie
     */
    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * Set the movie to the given categories.
     * @param categories the categories to be set to the movie
     */
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     * Add the given category to the category set of this movie.
     * @param category the category to be added
     */
    public void addCategory(Category category) {
        category.addMovie(this);
        categories.add(category);
    }

    /**
     * Remove the given category from the category set of this movie.
     * @param category the category to be removed
     */
    public void removeCategory(Category category) {
        category.removeMovie(this);
        categories.remove(category);
    }

    /**
     * Return the actors of the movie.
     * @return the actors of the movie
     */
    public Set<Actor> getActors() {
        return actors;
    }

    /**
     * Set the movie to the given actors.
     * @param actors the actors to be set to the movie
     */
    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Add the given actor to the actor set of this movie.
     * @param actor the actor to be added
     */
    public void addActor(Actor actor) {
        actor.addMovie(this);
        actors.add(actor);
    }

    /**
     * Remove the given actor from the actor set of this movie.
     * @param actor the actor to be removed
     */
    public void removeActor(Actor actor) {
        actor.removeMovie(this);
        actors.remove(actor);
    }

    /**
     * Return the countries of the movie.
     * @return the countries of the movie
     */
    public Set<Country> getCountries() {
        return countries;
    }

    /**
     * Set the movie to the given countries.
     * @param countries the countries to be set to the movie
     */
    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    /**
     * Add the given country to the country set of this movie.
     * @param country the country to be added
     */
    public void addCountry(Country country) {
        country.addMovie(this);
        countries.add(country);
    }

    /**
     * Remove the given country from the country set of this movie.
     * @param country the country to be removed
     */
    public void removeCountry(Country country) {
        country.removeMovie(this);
        countries.remove(country);
    }

    /**
     * Return the description of the movie.
     * @return the description of the movie
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the movie to the given description.
     * @param description the description to be set to the movie
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the review of the movie.
     * @return the review of the movie
     */
    public String getReview() {
        return review;
    }

    /**
     * Set the movie to the given review.
     * @param review the review to be set to the movie
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Return the poster of the movie.
     * @return the poster of the movie
     */
    public byte[] getPoster() {
        return poster;
    }

    /**
     * Set the movie to the given poster.
     * @param poster the poster to be set to the movie
     */
    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    /**
     * Load the image from the filepath and set it to the poster of the movie.
     * @param filepath the path of the poster file
     */
    public void loadPoster(String filepath) {
        try {
            File image = new File("src/main/resources/static/images/posters/" + filepath);
            byte[] poster = Files.readAllBytes(image.toPath());
            setPoster(poster);
        } catch (IOException E) {
            System.out.println("Cannot load the poster");
        }
    }

    /**
     * Return the category names of the movie, seperated by "/"
     * @return the category names of the movie
     */
    public String getCategoryName() {
        return String.join("/", categories.stream().map(c -> c.getName()).collect(Collectors.toSet()));
    }

    /**
     * Return the country names of the movie, seperated by "/"
     * @return the country names of the movie
     */
    public String getCountryName() {
        return String.join("/", countries.stream().map(c -> c.getName()).collect(Collectors.toSet()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(length, movie.length) && Objects.equals(rating, movie.rating) && Objects.equals(releaseDate, movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, length, rating, releaseDate);
    }

}
