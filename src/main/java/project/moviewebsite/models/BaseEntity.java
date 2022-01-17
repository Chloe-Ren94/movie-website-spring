package project.moviewebsite.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * This class implements the Serializable interface.
 * It represents any entity with an id.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Return the id of the entity.
     * @return the id of the entity
     */
    public Long getId() {
        return id;
    }


    /**
     * Set the entity with the given id.
     * @param id the id to be set to this entity.
     */
    public void setId(Long id) {
        this.id = id;
    }

}
