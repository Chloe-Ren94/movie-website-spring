package project.moviewebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.moviewebsite.models.Country;
import project.moviewebsite.services.ActorService;
import project.moviewebsite.services.CountryService;
import project.moviewebsite.services.MovieService;

/**
 * This class represents a controller for the country page.
 */
@Controller
public class CountryController {

    private final CountryService countryService;
    private final MovieService movieService;
    private final ActorService actorService;

    /**
     * Construct a CountryController with the given countryService, movieService and actorService.
     * @param countryService the countryService of the CountryController
     * @param movieService the movieService of the CountryController
     * @param actorService the actorService of the CountryController
     */
    public CountryController(CountryService countryService, MovieService movieService, ActorService actorService) {
        this.countryService = countryService;
        this.movieService = movieService;
        this.actorService = actorService;
    }

    /**
     * Find and display all countries.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("/countries")
    public String displayAll(Model model) {
        model.addAttribute("countries", countryService.findAll());
        return "country/index";
    }

    /**
     * Find and display the country with the given id.
     * @param id the id of the country
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping({"/country/{id}", "/country/{id}/display"})
    public String displayById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("country", countryService.findById(id));
        return "country/detail";
    }

    /**
     * Return a country add form.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("country/add")
    public String addCountry(Model model){
        model.addAttribute("country", new Country());
        return "country/form";
    }

    /**
     * Return an update form for the country with the given id.
     * @param id the id of the country
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("/country/{id}/update")
    public String updateCountry(@PathVariable("id") Long id, Model model){
        model.addAttribute("country", countryService.findById(id));
        return "country/form";
    }

    /**
     * Save the new country to the database.
     * @param country the country to be added to the database
     * @param bindingResult the bindingResult of the form
     * @return the new created country page if successful, otherwise return the add form
     */
    @PostMapping("country")
    public String addOrUpdate(@Validated @ModelAttribute Country country, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "country/form";

        Country savedCountry = countryService.save(country);
        return "redirect:/country/" + savedCountry.getId() + "/display";
    }

    /**
     * Delete the country with the given id.
     * @param id the id of the country
     * @return the redirect url of country index page
     */
    @GetMapping("country/{id}/delete")
    public String deleteById(@PathVariable("id") Long id) {
        Country country = countryService.findById(id);
        movieService.deleteCountry(country);
        actorService.deleteCountry(country);
        countryService.delete(country);
        return "redirect:/countries";
    }

}
