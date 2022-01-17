package project.moviewebsite.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.moviewebsite.models.Actor;
import project.moviewebsite.models.Country;
import project.moviewebsite.services.ActorService;
import project.moviewebsite.services.CountryService;
import project.moviewebsite.services.MovieService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents a controller for the actor page.
 */
@Controller
public class ActorController {

    private final ActorService actorService;
    private final CountryService countryService;
    private final MovieService movieService;

    /**
     * Construct an ActorController with the given actorService, countryService and movieService.
     * @param actorService the actorService of the ActorController
     * @param countryService the countryService of the ActorController
     * @param movieService the movieService of the ActorController
     */
    public ActorController(ActorService actorService, CountryService countryService, MovieService movieService) {
        this.actorService = actorService;
        this.countryService = countryService;
        this.movieService = movieService;
    }

    /**
     * Find, sort and display all actors.
     * @param field the field to be sorted
     * @param page the page number of the current page
     * @param model the model to be sent to the view
     * @return the view name
     */
    @RequestMapping("/actors/sortby/{field}/page/{page}")
    public String sortAndDisplayAll(@PathVariable String field, @PathVariable int page, Model model) {
        Sort sort = Sort.by(field).ascending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Actor> actorPage = actorService.findAll(pageable);

        int totalPages = actorPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        else
            model.addAttribute("pageNumbers", null);

        model.addAttribute("sortField", field);
        model.addAttribute("actorPage", actorPage);
        model.addAttribute("actorList", actorPage.getContent());
        return "actor/index-pagination";
    }

    /**
     * Find and display the actor with the given id.
     * @param id the id of the actor
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping({"/actor/{id}/display", "/actor/{id}"})
    public String displayById(@PathVariable Long id, Model model) {
        model.addAttribute("actor", actorService.findById(id));
        return "actor/detail";
    }

    /**
     * Return an actor add form.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("actor/add")
    public String addActor(Model model){
        Actor actor = new Actor();
        actor.setCountry(new Country());
        model.addAttribute("actor", actor);
        model.addAttribute("countries", countryService.findAll());
        return "actor/form";
    }

    /**
     * Return an update form for the actor with the given id.
     * @param id the id of the actor
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("/actor/{id}/update")
    public String updateActor(@PathVariable Long id, Model model){
        Actor actor = actorService.findById(id);
        if (actor.getCountry() == null)
            actor.setCountry(new Country());
        model.addAttribute("actor", actor);
        model.addAttribute("countries", countryService.findAll());
        return "actor/form";
    }

    /**
     * Save the new actor to the database.
     * @param actor the actor to be added to the database
     * @param bindingResult the bindingResult of the form
     * @param model the model to be sent to the view
     * @return the new created actor page if successful, otherwise return the add form
     */
    @PostMapping("actor")
    public String addOrUpdate(@Validated @ModelAttribute Actor actor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countryService.findAll());
            return "actor/form";
        }

        Actor savedActor = actorService.save(actor);
        return "redirect:/actor/" + savedActor.getId() + "/display";
    }

    /**
     * Delete the actor with the given id.
     * @param id the id of the actor
     * @return the redirect url of actor index page
     */
    @GetMapping("actor/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        movieService.deleteActor(actor);
        actorService.delete(actor);
        return "redirect:/actors/sortby/id/page/1";
    }

    /**
     * Return an actor find form.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("actor/find")
    public String findActor(Model model){
        model.addAttribute("actor", new Actor());
        return "actor/findform";
    }

    /**
     * Find all actors containing the given name.
     * @param actor the actor in the find form
     * @param result the BindingResult of the form
     * @param model the model to be sent to the view
     * @return the find form if there are no actors, the detailed actor page if there is one actor,
     *         the index page if there are more than one actor.
     */
    @PostMapping("actor/finding")
    public String processFindForm(@ModelAttribute Actor actor, BindingResult result, Model model) {
        if (actor.getName() == null)
            actor.setName("");

        List<Actor> actors = actorService.findByNameContainingIgnoreCase(actor.getName());

        if (actors.isEmpty()) {
            result.rejectValue("name", "notFound", "Not found");
            return "actor/findform";
        }

        if (actors.size() == 1) {
            actor = actors.get(0);
            return "redirect:/actor/" + actor.getId() + "/display";
        }

        model.addAttribute("actors", actors);
        return "actor/index";
    }

    /**
     * Return the image upload form of the actor.
     * @param id the id of the actor
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("actor/{id}/image")
    public String showUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("actor", actorService.findById(id));
        return "actor/imageuploadform";
    }

    /**
     * Set the image to the photo of the actor.
     * @param id the id of the actor.
     * @param file the photo file to be set
     * @return the redirect url of the actor with the given id
     */
    @PostMapping("actor/{id}/image")
    public String handlePhotoPost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        actorService.savePhoto(id, file);
        return "redirect:/actor/" + id + "/display";
    }

    /**
     * Display the photo of the actor with the given id.
     * @param id the id of the actor
     * @param response the HTTP Servlet Response
     * @throws IOException when the photo cannot be rendered
     */
    @GetMapping("actor/{id}/photo")
    public void renderPhotoFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Actor actor = actorService.findById(id);
        response.setContentType("image/jpeg");
        byte[] photo;

        if (actor.getPhoto() != null)
            photo = actor.getPhoto();
        else {
            File image = new File("src/main/resources/static/images/photos/default.jpg");
            photo = Files.readAllBytes(image.toPath());
        }

        InputStream is = new ByteArrayInputStream(photo);
        IOUtils.copy(is, response.getOutputStream());
    }

}
