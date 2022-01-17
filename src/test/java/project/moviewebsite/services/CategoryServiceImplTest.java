package project.moviewebsite.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.moviewebsite.models.Category;
import project.moviewebsite.repositories.CategoryRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests the CategoryServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void findAll() {
        Category category = new Category("Action");
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(category);

        when(categoryRepository.findAll()).thenReturn(categorySet);
        Set<Category> categories = categoryService.findAll();

        assertEquals(1, categories.size());
        verify(categoryRepository, times(1)).findAll();
        verify(categoryRepository, never()).findById(anyLong());
    }

    @Test
    void findById() {
        Category category = new Category("Action");
        category.setId(3L);
        Optional<Category> categoryOptional = Optional.of(category);

        when(categoryRepository.findById(anyLong())).thenReturn(categoryOptional);
        Category categoryReturned = categoryService.findById(3L);

        assertEquals(categoryOptional.get(), categoryReturned);
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, never()).findAll();
    }

    @Test
    void save() {
        Category category = new Category("Romance");
        category.setId(2L);

        when(categoryRepository.save(any())).thenReturn(category);
        Category categoryReturned = categoryService.save(category);

        assertEquals(category, categoryReturned);
        verify(categoryRepository, times(1)).save(any());
        verify(categoryRepository, never()).findById(anyLong());
    }

    @Test
    void delete() {
        Category category = new Category("Romance");
        category.setId(6L);

        categoryService.delete(category);

        verify(categoryRepository, times(1)).delete(any());
    }

}