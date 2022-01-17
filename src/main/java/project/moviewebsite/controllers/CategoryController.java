package project.moviewebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.moviewebsite.models.Category;
import project.moviewebsite.services.CategoryService;
import project.moviewebsite.services.MovieService;

/**
 * This class represents a controller for the category page.
 */
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final MovieService movieService;

    /**
     * Construct a CategoryController with the given categoryService and movieService.
     * @param categoryService the categoryService of the CategoryController
     * @param movieService the movieService of the CategoryController
     */
    public CategoryController(CategoryService categoryService, MovieService movieService) {
        this.categoryService = categoryService;
        this.movieService = movieService;
    }

    /**
     * Find and display all categories.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("/categories")
    public String displayAll(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/index";
    }

    /**
     * Find and display the category with the given id.
     * @param id the id of the category
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping({"/category/{id}", "/category/{id}/display"})
    public String displayById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "category/detail";
    }

    /**
     * Return a category add form.
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("category/add")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "category/form";
    }

    /**
     * Return an update form for the category with the given id.
     * @param id the id of the category
     * @param model the model to be sent to the view
     * @return the view name
     */
    @GetMapping("/category/{id}/update")
    public String updateCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "category/form";
    }

    /**
     * Save the new category to the database.
     * @param category the category to be added to the database
     * @param bindingResult the bindingResult of the form
     * @return the new created category page if successful, otherwise return the add form
     */
    @PostMapping("category")
    public String addOrUpdate(@Validated @ModelAttribute Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "category/form";

        Category savedCategory = categoryService.save(category);
        return "redirect:/category/" + savedCategory.getId() + "/display";
    }

    /**
     * Delete the category with the given id.
     * @param id the id of the category
     * @return the redirect url of category index page
     */
    @GetMapping("category/{id}/delete")
    public String deleteById(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        movieService.deleteCategory(category);
        categoryService.delete(category);
        return "redirect:/categories";
    }

}

