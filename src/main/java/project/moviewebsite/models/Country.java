package project.moviewebsite.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a country of the movie and actor.
 */
@Entity
public class Country extends BaseEntity {

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.PERSIST)
    private Set<Actor> actors = new HashSet<>();

    /**
     * Construct a country with no argument. All fields will be set to the default value.
     */
    public Country() {}

    /**
     * Construct a country with the given name.
     * @param name the name of the country
     */
    public Country(String name) {
        this.name = name;
    }

    /**
     * Return the name of the country.
     * @return the name of the country
     */
    public String getName() {
        return name;
    }

    /**
     * Set the country to the given name.
     * @param name the name to be set to the country
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the movies of the country.
     * @return the movies of the country
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set the country to the given movies.
     * @param movies the movies to be set to the country
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Add the given movie to the movie set of this country.
     * @param movie the movie to be added
     */
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    /**
     * Remove the given movie from the movie set of this country.
     * @param movie the movie to be removed
     */
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    /**
     * Return the actors of the country.
     * @return the actors of the country
     */
    public Set<Actor> getActors() {
        return actors;
    }

    /**
     * Set the country to the given actors.
     * @param actors the actors to be set to the country
     */
    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
