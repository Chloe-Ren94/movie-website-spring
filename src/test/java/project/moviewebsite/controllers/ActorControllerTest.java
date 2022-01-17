package project.moviewebsite.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import project.moviewebsite.models.Actor;
import project.moviewebsite.services.ActorService;
import project.moviewebsite.services.CountryService;
import project.moviewebsite.services.MovieService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests the ActorController class.
 */
@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    private MockMvc mockMvc;
    private ActorController actorController;

    @Mock
    private ActorService actorService;

    @Mock
    private CountryService countryService;

    @Mock
    private MovieService movieService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        actorController = new ActorController(actorService, countryService, movieService);
        mockMvc = MockMvcBuilders.standaloneSetup(actorController).build();
    }

    @Test
    void sortAndDisplayAll() throws Exception {
        Actor a1 = new Actor();
        a1.setId(3L);
        Actor a2 = new Actor();
        a2.setId(5L);
        List<Actor> actors = new ArrayList<>();
        actors.add(a1);
        actors.add(a2);
        Page<Actor> actorPage = new PageImpl<>(actors);

        ArgumentCaptor<Page<Actor>> argumentCaptor = ArgumentCaptor.forClass(Page.class);
        when(actorService.findAll(any())).thenReturn(actorPage);

        actorController.sortAndDisplayAll("id", 1, model);
        verify(actorService, times(1)).findAll(any());
        verify(model, times(1)).addAttribute(eq("actorPage"), argumentCaptor.capture());
        Page<Actor> actorPageReturned = argumentCaptor.getValue();
        assertEquals(1, actorPageReturned.getTotalPages());
        assertEquals(2, actorPageReturned.getTotalElements());

        mockMvc.perform(get("/actors/sortby/id/page/1")).andExpect(status().isOk())
                .andExpect(view().name("actor/index-pagination"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(model().attributeExists("sortField"))
                .andExpect(model().attributeExists("actorList"));
    }

    @Test
    void displayById() throws Exception {
        Actor actor = new Actor();
        actor.setId(8L);

        ArgumentCaptor<Actor> argumentCaptor = ArgumentCaptor.forClass(Actor.class);
        when(actorService.findById(anyLong())).thenReturn(actor);

        actorController.displayById(anyLong(), model);
        verify(actorService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("actor"), argumentCaptor.capture());
        assertEquals(actor, argumentCaptor.getValue());

        mockMvc.perform(get("/actor/8/display")).andExpect(status().isOk())
                .andExpect(view().name("actor/detail"));
    }

    @Test
    void addActor() throws Exception {
        mockMvc.perform(get("/actor/add")).andExpect(status().isOk())
                .andExpect(view().name("actor/form"))
                .andExpect(model().attributeExists("actor"))
                .andExpect(model().attributeExists("countries"));
    }

    @Test
    void updateActor() throws Exception {
        Actor actor = new Actor();
        actor.setId(8L);
        when(actorService.findById(anyLong())).thenReturn(actor);

        mockMvc.perform(get("/actor/8/update")).andExpect(status().isOk())
                .andExpect(view().name("actor/form"))
                .andExpect(model().attributeExists("actor"))
                .andExpect(model().attributeExists("countries"));
    }

    @Test
    void addOrUpdate() throws Exception {
        Actor actor = new Actor();
        actor.setId(8L);
        when(actorService.save(any())).thenReturn(actor);

        mockMvc.perform(post("/actor")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Yuki"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/actor/8/display"));
    }

    @Test
    void invalidPost() throws Exception {
        mockMvc.perform(post("/actor")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("actor/form"));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/actor/5/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/actors/sortby/id/page/1"));

        verify(actorService, times(1)).findById(anyLong());
        verify(movieService, times(1)).deleteActor(any());
        verify(actorService, times(1)).delete(any());
    }

    @Test
    void findActor() throws Exception {
        mockMvc.perform(get("/actor/find")).andExpect(status().isOk())
                .andExpect(view().name("actor/findform"))
                .andExpect(model().attributeExists("actor"));
    }

    @Test
    void processNotFind() throws Exception {
        List<Actor> actors = new ArrayList<>();
        when(actorService.findByNameContainingIgnoreCase(anyString())).thenReturn(actors);

        mockMvc.perform(post("/actor/finding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("actor/findform"));
    }

    @Test
    void processFindOne() throws Exception {
        List<Actor> actors = new ArrayList<>();
        Actor actor = new Actor();
        actor.setId(1L);
        actors.add(actor);

        when(actorService.findByNameContainingIgnoreCase(anyString())).thenReturn(actors);

        mockMvc.perform(post("/actor/finding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/actor/1/display"));
    }

    @Test
    void processFindMany() throws Exception {
        List<Actor> actors = new ArrayList<>();
        Actor a1 = new Actor();
        a1.setId(1L);
        Actor a2 = new Actor();
        a2.setId(5L);
        actors.add(a1);
        actors.add(a2);

        when(actorService.findByNameContainingIgnoreCase(anyString())).thenReturn(actors);

        mockMvc.perform(post("/actor/finding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("actor/index"))
                .andExpect(model().attributeExists("actors"));
    }

    @Test
    void showUploadForm() throws Exception {
        Actor actor = new Actor();
        actor.setId(2L);
        when(actorService.findById(anyLong())).thenReturn(actor);

        mockMvc.perform(get("/actor/2/image")).andExpect(status().isOk())
                .andExpect(view().name("actor/imageuploadform"))
                .andExpect(model().attributeExists("actor"));
    }

    @Test
    void handlePhotoPost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "Actor test".getBytes());

        mockMvc.perform(multipart("/actor/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/actor/1/display"));

        verify(actorService, times(1)).savePhoto(anyLong(), any());
    }

    @Test
    void renderPhotoFromDB() throws Exception {
        Actor actor = new Actor();
        actor.setId(4L);
        byte[] bytes = "mock photo test".getBytes();
        actor.setPhoto(bytes);
        when(actorService.findById(anyLong())).thenReturn(actor);

        MockHttpServletResponse response = mockMvc.perform(get("/actor/4/photo"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] bytesResponse = response.getContentAsByteArray();
        assertArrayEquals(bytes, bytesResponse);
    }

}