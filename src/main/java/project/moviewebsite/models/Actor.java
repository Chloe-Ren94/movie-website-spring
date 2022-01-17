package project.moviewebsite.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents an actor.
 */
@Entity
public class Actor extends BaseEntity {

    @NotBlank
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();

    @ManyToOne()
    private Country country;

    @Lob
    private byte[] photo;

    /**
     * Construct an actor with no argument. All fields will be set to the default value.
     */
    public Actor() {}

    /**
     * Construct an actor with the given name, born year, month and day, gender, and country.
     * @param name the name of the actor
     * @param year the born year of the actor
     * @param month the born month of the actor
     * @param day the born day of the actor
     * @param gender the gender of the actor
     * @param country the country of the actor
     * @throws IllegalArgumentException when the born year, month and day is invalid
     */
    public Actor(String name, int year, int month, int day, Gender gender, Country country) throws IllegalArgumentException {
        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
            this.name = name;
            this.birthDate = birthDate;
            this.gender = gender;
            this.country = country;
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid birth date");
        }
    }

    /**
     * Return the name of the actor.
     * @return the name of the actor
     */
    public String getName() {
        return name;
    }

    /**
     * Set the actor to the given name.
     * @param name the name to be set to the actor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the birth date of the actor.
     * @return the birth date of the actor
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Set the actor to the given birth date.
     * @param birthDate the birth date to be set to the actor
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Return the gender of the actor.
     * @return the gender of the actor
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set the actor to the given gender.
     * @param gender the gender to be set to the actor
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Return the movies of the actor.
     * @return the movies of the actor
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set the actor to the given movies.
     * @param movies the movies to be set to the actor
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Add the given movie to the movie set of this actor.
     * @param movie the movie to be added
     */
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    /**
     * Remove the given movie from the movie set of this actor.
     * @param movie the movie to be removed
     */
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    /**
     * Return the country of the actor.
     * @return the country of the actor
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set the actor to the given country.
     * @param country the country to be set to the actor
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Return the photo of the actor.
     * @return the photo of the actor
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * Set the actor to the given photo.
     * @param photo the photo to be set to the actor
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * Load the image from the filepath and set it to the photo of the actor.
     * @param filepath the path of the photo file
     */
    public void loadPhoto(String filepath) {
        try {
            File image = new File("src/main/resources/static/images/photos/" + filepath);
            byte[] photo = Files.readAllBytes(image.toPath());
            setPhoto(photo);
        } catch (IOException e) {
            System.out.println("Cannot load the photo");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) && Objects.equals(birthDate, actor.birthDate) && gender == actor.gender && Objects.equals(country, actor.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, gender, country);
    }

}
