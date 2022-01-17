package project.moviewebsite.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import project.moviewebsite.models.Category;
import project.moviewebsite.services.CategoryService;
import project.moviewebsite.services.MovieService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests the CategoryController class.
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private MovieService movieService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(categoryService, movieService);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void displayAll() throws Exception {
        Category c1 = new Category("Drama");
        Category c2 = new Category("History");
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(c1);
        categorySet.add(c2);

        ArgumentCaptor<Set<Category>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        when(categoryService.findAll()).thenReturn(categorySet);

        categoryController.displayAll(model);
        verify(categoryService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("categories"), argumentCaptor.capture());
        Set<Category> categories = argumentCaptor.getValue();
        assertEquals(2, categories.size());

        mockMvc.perform(get("/categories")).andExpect(status().isOk())
                .andExpect(view().name("category/index"));
    }

    @Test
    void displayById() throws Exception {
        Category category = new Category("Drama");
        category.setId(1L);

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        when(categoryService.findById(anyLong())).thenReturn(category);

        categoryController.displayById(anyLong(), model);
        verify(categoryService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("category"), argumentCaptor.capture());
        assertEquals(category, argumentCaptor.getValue());

        mockMvc.perform(get("/category/1/display")).andExpect(status().isOk())
                .andExpect(view().name("category/detail"));
    }

    @Test
    void addCategory() throws Exception {
        mockMvc.perform(get("/category/add")).andExpect(status().isOk())
                .andExpect(view().name("category/form"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void updateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        when(categoryService.findById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/category/1/update")).andExpect(status().isOk())
                .andExpect(view().name("category/form"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void addOrUpdate() throws Exception {
        Category category = new Category();
        category.setId(2L);
        when(categoryService.save(any())).thenReturn(category);

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "History"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category/2/display"));
    }

    @Test
    void invalidPost() throws Exception {
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("category/form"));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/category/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories"));

        verify(categoryService, times(1)).findById(anyLong());
        verify(movieService, times(1)).deleteCategory(any());
        verify(categoryService, times(1)).delete(any());
    }

}