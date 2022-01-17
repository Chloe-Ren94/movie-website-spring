package project.moviewebsite.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import project.moviewebsite.models.Actor;
import project.moviewebsite.models.Country;
import project.moviewebsite.models.Gender;
import project.moviewebsite.repositories.ActorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * This class tests the ActorServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    private ActorService actorService;

    @Mock
    private ActorRepository actorRepository;

    @BeforeEach
    void setUp() {
        actorService = new ActorServiceImpl(actorRepository);
    }

    @Test
    void findAll() {
        Actor actor = new Actor();
        Set<Actor> actorSet = new HashSet<>();
        actorSet.add(actor);

        when(actorRepository.findAll()).thenReturn(actorSet);
        Set<Actor> actors = actorService.findAll();

        assertEquals(1, actors.size());
        assertEquals(actorSet, actors);
        verify(actorRepository, times(1)).findAll();
        verify(actorRepository, never()).findById(anyLong());
    }

    @Test
    void findById() {
        Actor actor = new Actor();
        actor.setId(6L);
        Optional<Actor> actorOptional = Optional.of(actor);

        when(actorRepository.findById(anyLong())).thenReturn(actorOptional);
        Actor actorReturned = actorService.findById(6L);

        assertEquals(actorOptional.get(), actorReturned);
        verify(actorRepository, times(1)).findById(anyLong());
        verify(actorRepository, never()).findAll();
    }

    @Test
    void save() {
        Actor actor = new Actor();
        actor.setName("Michiko");

        when(actorRepository.save(any())).thenReturn(actor);
        Actor actorReturned = actorService.save(actor);

        assertEquals(actor, actorReturned);
        verify(actorRepository, times(1)).save(any());
        verify(actorRepository, never()).findById(anyLong());
    }

    @Test
    void delete() {
        Actor actor = new Actor();
        actor.setName("Nanako");

        actorService.delete(actor);

        verify(actorRepository, times(1)).delete(any());
    }

    @Test
    void deleteCountry() {
        Country c1 = new Country("Japan");
        Country c2 = new Country("Sweden");

        Actor a1 = new Actor("Yuki", 1967, 11, 16, Gender.FEMALE, c1);
        Actor a2 = new Actor("Michiko", 1967, 5, 23, Gender.FEMALE, c1);
        Actor a3 = new Actor("Test", 1998, 2, 5, Gender.UNBINARY, c2);
        Set<Actor> actorSet = new HashSet<>();
        actorSet.add(a1);
        actorSet.add(a2);
        actorSet.add(a3);

        when(actorRepository.findAll()).thenReturn(actorSet);
        when(actorRepository.findById(1L)).thenReturn(Optional.of(a1));
        when(actorRepository.findById(2L)).thenReturn(Optional.of(a2));
        when(actorRepository.findById(3L)).thenReturn(Optional.of(a3));
        actorService.deleteCountry(c1);

        assertEquals(null, actorService.findById(1L).getCountry());
        assertEquals(null, actorService.findById(2L).getCountry());
        assertEquals(c2, actorService.findById(3L).getCountry());
    }

    @Test
    void findByNameContainingIgnoreCase() {
        Actor actor = new Actor();
        List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);

        when(actorRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(actorList);
        List<Actor> actors = actorService.findByNameContainingIgnoreCase(anyString());

        assertEquals(1, actors.size());
        assertEquals(actorList, actors);
        verify(actorRepository, times(1)).findByNameContainingIgnoreCase(anyString());
        verify(actorRepository, never()).findAll();
        verify(actorRepository, never()).findById(anyLong());
    }

    @Test
    void savePhoto() throws Exception {
        Actor actor = new Actor();
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "Actor test".getBytes());

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));
        when(actorRepository.save(any())).thenReturn(actor);

        actorService.savePhoto(anyLong(), multipartFile);
        assertEquals(multipartFile.getBytes(), actor.getPhoto());
    }

}