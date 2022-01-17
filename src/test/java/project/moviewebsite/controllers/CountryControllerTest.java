package project.moviewebsite.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import project.moviewebsite.models.Country;
import project.moviewebsite.services.ActorService;
import project.moviewebsite.services.CountryService;
import project.moviewebsite.services.MovieService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests the CountryController class.
 */
@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    private MockMvc mockMvc;
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Mock
    private MovieService movieService;

    @Mock
    private ActorService actorService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        countryController = new CountryController(countryService, movieService, actorService);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    void displayAll() throws Exception {
        Country c1 = new Country("Denmark");
        Country c2 = new Country("Canada");
        Set<Country> countrySet = new HashSet<>();
        countrySet.add(c1);
        countrySet.add(c2);

        ArgumentCaptor<Set<Country>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        when(countryService.findAll()).thenReturn(countrySet);

        countryController.displayAll(model);
        verify(countryService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("countries"), argumentCaptor.capture());
        Set<Country> countries = argumentCaptor.getValue();
        assertEquals(2, countries.size());

        mockMvc.perform(get("/countries")).andExpect(status().isOk())
                .andExpect(view().name("country/index"));
    }

    @Test
    void displayById() throws Exception {
        Country country = new Country("Denmark");
        country.setId(5L);

        ArgumentCaptor<Country> argumentCaptor = ArgumentCaptor.forClass(Country.class);
        when(countryService.findById(anyLong())).thenReturn(country);

        countryController.displayById(anyLong(), model);
        verify(countryService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("country"), argumentCaptor.capture());
        assertEquals(country, argumentCaptor.getValue());

        mockMvc.perform(get("/country/5/display")).andExpect(status().isOk())
                .andExpect(view().name("country/detail"));
    }

    @Test
    void addCountry() throws Exception {
        mockMvc.perform(get("/country/add")).andExpect(status().isOk())
                .andExpect(view().name("country/form"))
                .andExpect(model().attributeExists("country"));
    }

    @Test
    void updateCountry() throws Exception {
        Country country = new Country();
        country.setId(11L);
        when(countryService.findById(anyLong())).thenReturn(country);

        mockMvc.perform(get("/country/11/update")).andExpect(status().isOk())
                .andExpect(view().name("country/form"))
                .andExpect(model().attributeExists("country"));
    }

    @Test
    void addOrUpdate() throws Exception {
        Country country = new Country();
        country.setId(3L);
        when(countryService.save(any())).thenReturn(country);

        mockMvc.perform(post("/country")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "France"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/country/3/display"));
    }

    @Test
    void invalidPost() throws Exception {
        mockMvc.perform(post("/country")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("country/form"));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/country/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/countries"));

        verify(countryService, times(1)).findById(anyLong());
        verify(movieService, times(1)).deleteCountry(any());
        verify(actorService, times(1)).deleteCountry(any());
        verify(countryService, times(1)).delete(any());
    }

}