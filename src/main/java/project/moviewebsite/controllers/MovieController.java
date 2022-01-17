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
import project.moviewebsite.models.Movie;
import project.moviewebsite.services.*;

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
 * This class represents a controller for the movie page.
 */
@Controller
public class MovieController {

    private final MovieService movieService;
    private final CountryService countryService;
    private final CategoryService categoryService;
    private final ActorService actorService;

    /**
     * Construct a MovieController with the given movieService, countryService, categoryService and actorService.
     * @param movieService the movieService of the MovieController
     * @param countryService the countryService of the MovieController
     * @param categoryService the categoryService of the MovieController
     * @param actorService the actorService of the MovieController
     */
    public MovieController(MovieService movieService, CountryService countryService, CategoryService categoryService, ActorService actorService) {
        this.movieService = movieService;
        this.countryService = countryService;
        this.categoryService = categoryService;
        this.actorService = actorService;
    }

    /**
     * Find, sort and display all movies.
     * @param field the field to be sorted
     * @param page the page number of the current page
     * @param model the model to be sent to the view
     * @return the view name
     */
    //Referenced from https://www.baeldung.com/spring-data-jpa-pagination-sorting
    @RequestMapping("/movies/sortby/{field}/page/{page}")
    public String sortAndDisplayAll(@PathVariable String field, @PathVariable int page, Model model) {
        Sort sort;
        if (field.equals("rating"))
            sort = Sort.by(field).descending();
        else
            sort = Sort.by(field).ascending();

        Pageable pageable = PageRequest.of(page - 1, 5, sort);
        Page<Movie> moviePage = movieService.findAll(pageable);
        int totalPages = moviePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        else
            model.addAttribute("pageNumbers", null);

        model.addAttribute("sortField", field);
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("movieList", moviePage.getContent());
        return "movie/index-pagination";
    }

    /**
     * Find and display the movie with the given id.
     * @param id the id of the movie
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping({"/movie/{id}/display", "/movie/{id}"})
    public String displayById(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        return "movie/detail";
    }

    /**
     * Return a movie add form.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("movie/add")
    public String addMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("allCountries", countryService.findAll());
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("allActors", actorService.findAll());
        return "movie/form";
    }

    /**
     * Return an update form for the movie with the given id.
     * @param id the id of the movie
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("movie/{id}/update")
    public String updateMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("allCountries", countryService.findAll());
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("allActors", actorService.findAll());
        return "movie/form";
    }

    /**
     * Save the new movie to the database.
     * @param movie the movie to be added to the database
     * @param bindingResult the bindingResult of the form
     * @param model the model to be sent to the view
     * @return the new created movie page if successful, otherwise return the add form
     */
    @PostMapping("movie")
    public String addOrUpdate(@Validated @ModelAttribute Movie movie, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCountries", countryService.findAll());
            model.addAttribute("allCategories", categoryService.findAll());
            model.addAttribute("allActors", actorService.findAll());
            return "movie/form";
        }

        Movie savedMovie = movieService.save(movie);
        return "redirect:/movie/" + savedMovie.getId() + "/display";
    }

    /**
     * Delete the movie with the given id.
     * @param id the id of the movie
     * @return the redirect url of movie index page
     */
    @GetMapping("movie/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        movieService.delete(movie);
        return "redirect:/";
    }

    /**
     * Return an movie find form.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("movie/find")
    public String findMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "movie/findform";
    }

    /**
     * Find all movies containing the given title.
     * @param movie the movie in the find form
     * @param result the BindingResult of the form
     * @param model the model to be sent to the view
     * @return the find form if there are no movies, the detailed movie page if there is one movie,
     *         the index page if there are more than one movie.
     */
    @PostMapping("movie/finding")
    public String processFindForm(@ModelAttribute Movie movie, BindingResult result, Model model) {
        if (movie.getTitle() == null)
            movie.setTitle("");

        List<Movie> movies = movieService.findByTitleContainingIgnoreCase(movie.getTitle());

        if (movies.isEmpty()) {
            result.rejectValue("title", "notFound", "Not found");
            return "movie/findform";
        }

        if (movies.size() == 1) {
            movie = movies.get(0);
            return "redirect:/movie/" + movie.getId() + "/display";
        }

        model.addAttribute("movies", movies);
        return "movie/index";
    }

    /**
     * Return the image upload form of the movie.
     * @param id the id of the movie
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("movie/{id}/image")
    public String showUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        return "movie/imageuploadform";
    }

    /**
     * Set the image to the poster of the movie.
     * @param id the id of the movie.
     * @param file the poster file to be set
     * @return the redirect url of the movie with the given id
     */
    @PostMapping("movie/{id}/image")
    public String handlePosterPost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        movieService.savePoster(id, file);
        return "redirect:/movie/" + id + "/display";
    }

    /**
     * Display the poster of the movie with the given id.
     * @param id the id of the movie
     * @param response the HTTP Servlet Response
     * @throws IOException when the poster cannot be rendered
     */
    //Referenced from https://www.baeldung.com/spring-mvc-image-media-data
    @GetMapping("movie/{id}/poster")
    public void renderPosterFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Movie movie = movieService.findById(id);
        response.setContentType("image/jpeg");
        byte[] poster;

        if (movie.getPoster() != null)
            poster = movie.getPoster();
        else {
            File image = new File("src/main/resources/static/images/posters/default.jpg");
            poster = Files.readAllBytes(image.toPath());
        }

        InputStream is = new ByteArrayInputStream(poster);
        IOUtils.copy(is, response.getOutputStream());
    }

}
