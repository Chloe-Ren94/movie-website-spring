package project.moviewebsite.services;

import org.springframework.stereotype.Service;
import project.moviewebsite.models.Country;
import project.moviewebsite.repositories.CountryRepository;

/**
 * This class represents a Spring Data JPA implementation of the CountryService interface.
 */
@Service
public class CountryServiceImpl extends CrudServiceImpl<Country, CountryRepository> implements CountryService {

    /**
     * Construct a CountryServiceImpl with the given countryRepository.
     * @param countryRepository the countryRepository of the CountryServiceImpl
     */
    public CountryServiceImpl(CountryRepository countryRepository) {
        super(countryRepository);
    }

}
