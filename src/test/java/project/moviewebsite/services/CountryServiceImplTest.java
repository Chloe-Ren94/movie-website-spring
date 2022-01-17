package project.moviewebsite.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.moviewebsite.models.Country;
import project.moviewebsite.repositories.CountryRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * This class tests the CountryServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    private CountryService countryService;

    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(countryRepository);
    }

    @Test
    void findAll() {
        Country c1 = new Country("America");
        Country c2 = new Country("Ireland");
        Set<Country> countrySet = new HashSet<>();
        countrySet.add(c1);
        countrySet.add(c2);

        when(countryRepository.findAll()).thenReturn(countrySet);
        Set<Country> countries = countryService.findAll();

        assertEquals(2, countries.size());
        assertEquals(countrySet, countries);
        verify(countryRepository, times(1)).findAll();
        verify(countryRepository, never()).findById(anyLong());
    }

    @Test
    void findById() {
        Country country = new Country("Ireland");
        country.setId(6L);
        Optional<Country> countryOptional = Optional.of(country);

        when(countryRepository.findById(anyLong())).thenReturn(countryOptional);
        Country countryReturned = countryService.findById(6L);

        assertEquals(countryOptional.get(), countryReturned);
        verify(countryRepository, times(1)).findById(anyLong());
        verify(countryRepository, never()).findAll();
    }

    @Test
    void save() {
        Country country = new Country("Ireland");
        country.setId(6L);

        when(countryRepository.save(any())).thenReturn(country);
        Country countryReturned = countryService.save(country);

        assertEquals(country, countryReturned);
        verify(countryRepository, times(1)).save(any());
        verify(countryRepository, never()).findById(anyLong());
    }

    @Test
    void delete() {
        Country country = new Country("Ireland");
        country.setId(5L);

        countryService.delete(country);

        verify(countryRepository, times(1)).delete(any());
    }

}