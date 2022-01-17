package project.moviewebsite.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a category of the movie.
 */
@Entity
public class Category extends BaseEntity {

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Movie> movies = new HashSet<>();

    /**
     * Construct a category with no argument. All fields will be set to the default value.
     */
    public Category() {}

    /**
     * Construct a category with the given name.
     * @param name the name of the category
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Return the name of the category.
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Set the category to the given name.
     * @param name the name to be set to the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the movies of the category.
     * @return the movies of the category
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set the category to the given movies.
     * @param movies the movies to be set to the category
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Add the given movie to the movie set of this category.
     * @param movie the movie to be added
     */
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    /**
     * Remove the given movie from the movie set of this category.
     * @param movie the movie to be removed
     */
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
