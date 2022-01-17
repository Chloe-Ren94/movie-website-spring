package project.moviewebsite.repositories;

import org.springframework.data.repository.CrudRepository;
import project.moviewebsite.models.Category;

/**
 * This interface represents a repository for the Category class.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
