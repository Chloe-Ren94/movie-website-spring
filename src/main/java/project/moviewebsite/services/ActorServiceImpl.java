package project.moviewebsite.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.moviewebsite.models.Actor;
import project.moviewebsite.models.Country;
import project.moviewebsite.repositories.ActorRepository;

import java.io.IOException;
import java.util.List;

/**
 * This class represents a Spring Data JPA implementation of the ActorService interface.
 */
@Service
public class ActorServiceImpl extends CrudServiceImpl<Actor, ActorRepository> implements ActorService {

    /**
     * Construct an ActorServiceImpl with the given actorRepository.
     * @param actorRepository the actorRepository of the ActorServiceImpl
     */
    public ActorServiceImpl(ActorRepository actorRepository) {
        super(actorRepository);
    }

    @Override
    public Page<Actor> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteCountry(Country country) {
        for (Actor actor: findAll()) {
            if (actor.getCountry() != null && actor.getCountry().equals(country))
                actor.setCountry(null);
        }
    }

    @Override
    public List<Actor> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void savePhoto(Long actorId, MultipartFile file) {
        try {
            Actor actor = findById(actorId);
            actor.setPhoto(file.getBytes());
            save(actor);
        } catch (IOException e) {
            System.out.println("Cannot load the photo");
        }
    }

}
