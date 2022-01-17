package project.moviewebsite.repositories;

import org.springframework.data.repository.CrudRepository;
import project.moviewebsite.models.Country;

/**
 * This interface represents a repository for the Country class.
 */
public interface CountryRepository extends CrudRepository<Country, Long> {
}
