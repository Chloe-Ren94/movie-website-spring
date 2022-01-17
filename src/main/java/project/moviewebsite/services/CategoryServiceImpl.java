package project.moviewebsite.services;

import org.springframework.stereotype.Service;
import project.moviewebsite.models.Category;
import project.moviewebsite.repositories.CategoryRepository;

/**
 * This class represents a Spring Data JPA implementation of the CategoryService interface.
 */
@Service
public class CategoryServiceImpl extends CrudServiceImpl<Category, CategoryRepository> implements CategoryService {

    /**
     * Construct a CategoryServiceImpl with the given categoryRepository.
     * @param categoryRepository the categoryRepository of the CategoryServiceImpl
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }

}
