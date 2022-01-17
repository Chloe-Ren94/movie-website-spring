package project.moviewebsite.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import project.moviewebsite.models.Movie;
import project.moviewebsite.services.ActorService;
import project.moviewebsite.services.CategoryService;
import project.moviewebsite.services.CountryService;
import project.moviewebsite.services.MovieService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * This class tests the MovieController class.
 */
@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    private MockMvc mockMvc;
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private CountryService countryService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ActorService actorService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        movieController = new MovieController(movieService, countryService, categoryService, actorService);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void sortAndDisplayAll() throws Exception {
        Movie m1 = new Movie();
        m1.setId(3L);
        Movie m2 = new Movie();
        m2.setId(1L);
        List<Movie> movies = new ArrayList<>();
        movies.add(m1);
        movies.add(m2);
        Page<Movie> moviePage = new PageImpl<>(movies);

        ArgumentCaptor<Page<Movie>> argumentCaptor = ArgumentCaptor.forClass(Page.class);
        when(movieService.findAll(any())).thenReturn(moviePage);

        movieController.sortAndDisplayAll("id", 1, model);
        verify(movieService, times(1)).findAll(any());
        verify(model, times(1)).addAttribute(eq("moviePage"), argumentCaptor.capture());
        Page<Movie> moviePageReturned = argumentCaptor.getValue();
        assertEquals(1, moviePageReturned.getTotalPages());
        assertEquals(2, moviePageReturned.getTotalElements());

        mockMvc.perform(get("/movies/sortby/id/page/1")).andExpect(status().isOk())
                .andExpect(view().name("movie/index-pagination"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(model().attributeExists("sortField"))
                .andExpect(model().attributeExists("movieList"));
    }

    @Test
    void displayById() throws Exception {
        Movie movie = new Movie();
        movie.setId(7L);

        ArgumentCaptor<Movie> argumentCaptor = ArgumentCaptor.forClass(Movie.class);
        when(movieService.findById(anyLong())).thenReturn(movie);

        movieController.displayById(anyLong(), model);
        verify(movieService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("movie"), argumentCaptor.capture());
        assertEquals(movie, argumentCaptor.getValue());

        mockMvc.perform(get("/movie/7/display")).andExpect(status().isOk())
                .andExpect(view().name("movie/detail"));
    }

    @Test
    void addMovie() throws Exception {
        mockMvc.perform(get("/movie/add")).andExpect(status().isOk())
                .andExpect(view().name("movie/form"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("allCountries"))
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(model().attributeExists("allActors"));
    }

    @Test
    void updateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setId(8L);
        when(movieService.findById(anyLong())).thenReturn(movie);

        mockMvc.perform(get("/movie/8/update")).andExpect(status().isOk())
                .andExpect(view().name("movie/form"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("allCountries"))
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(model().attributeExists("allActors"));
    }

    @Test
    void addOrUpdate() throws Exception {
        Movie movie = new Movie();
        movie.setId(18L);
        when(movieService.save(any())).thenReturn(movie);

        mockMvc.perform(post("/movie")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "Secret")
                .param("length", "108"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/movie/18/display"));
    }

    @Test
    void invalidPost() throws Exception {
        mockMvc.perform(post("/movie")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/form"));

        mockMvc.perform(post("/movie")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Secret")
                .param("length", "-5"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/form"));

        mockMvc.perform(post("/movie")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Secret")
                .param("rating", "15.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/form"));
    }


    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/movie/5/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(movieService, times(1)).findById(anyLong());
        verify(movieService, times(1)).delete(any());
    }

    @Test
    void findMovie() throws Exception {
        mockMvc.perform(get("/movie/find")).andExpect(status().isOk())
                .andExpect(view().name("movie/findform"))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    void processNotFind() throws Exception {
        List<Movie> movies = new ArrayList<>();
        when(movieService.findByTitleContainingIgnoreCase(anyString())).thenReturn(movies);

        mockMvc.perform(post("/movie/finding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/findform"));
    }

    @Test
    void processFindOne() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setId(10L);
        movies.add(movie);

        when(movieService.findByTitleContainingIgnoreCase(anyString())).thenReturn(movies);

        mockMvc.perform(post("/movie/finding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/movie/10/display"));
    }

    @Test
    void processFindMany() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Movie m1 = new Movie();
        m1.setId(10L);
        movies.add(m1);
        Movie m2 = new Movie();
        m2.setId(5L);
        movies.add(m2);

        when(movieService.findByTitleContainingIgnoreCase(anyString())).thenReturn(movies);

        mockMvc.perform(post("/movie/finding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/index"))
                .andExpect(model().attributeExists("movies"));
    }

    @Test
    void showUploadForm() throws Exception {
        Movie movie = new Movie();
        movie.setId(2L);
        when(movieService.findById(anyLong())).thenReturn(movie);

        mockMvc.perform(get("/movie/2/image")).andExpect(status().isOk())
                .andExpect(view().name("movie/imageuploadform"))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    void handlePosterPost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "Chloe's Movie Website".getBytes());

        mockMvc.perform(multipart("/movie/9/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/movie/9/display"));

        verify(movieService, times(1)).savePoster(anyLong(), any());
    }

    @Test
    void renderPosterFromDB() throws Exception {
        Movie movie = new Movie();
        movie.setId(4L);
        byte[] bytes = "mock poster test".getBytes();
        movie.setPoster(bytes);
        when(movieService.findById(anyLong())).thenReturn(movie);

        MockHttpServletResponse response = mockMvc.perform(get("/movie/4/poster"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] bytesResponse = response.getContentAsByteArray();
        assertArrayEquals(bytes, bytesResponse);
    }

}