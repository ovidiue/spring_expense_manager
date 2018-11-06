package expense.web.controller;

import expense.model.Category;
import expense.service.CategoryService;
import expense.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    public CategoryController() {
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories-listing";
    }

    @RequestMapping({"categories/add"})
    public String addCategory(Model model) {
        log.info("Fetch add view");
        model.addAttribute("category", new Category());
        return "category-add";
    }

    @RequestMapping({"categories/edit/{catId}"})
    public String editCategory(@PathVariable Long catId, Model model) {
        log.info("Fetch edit view");
        Category category = this.categoryService.findById(catId).get();
        model.addAttribute("category", category);
        return "category-edit";
    }

    @RequestMapping(
            value = {"categories/delete/{catId}"},
            method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteCat(@PathVariable(name = "catId") Long catId, RedirectAttributes redirectAttributes) {
        log.info("deleteCat called");
        log.info("Cat id to delete: {}", catId);
        categoryService.findById(catId)
                .ifPresent(category -> {
                    log.info("tag to delete: {}", category);
                    expenseService.findAllWithCategory(category)
                            .forEach(expense -> {
                                log.info("expense category: {}", expense);
                                expense.setCategory(null);
                                expenseService.save(expense);
                            });
                    categoryService.deleteCategory(category);
                });
        return "redirect:/categories";
    }

    @RequestMapping(
            value = {"categories/edit/{catId}/update"},
            method = {RequestMethod.POST, RequestMethod.GET})
    public String editCat(@PathVariable(name = "catId") Long catId, Category category, RedirectAttributes redirectAttributes) {
        log.info("editCat called");
        log.info("Cat id to update: {}", catId);
        log.info("Cateogroy {}", category);
        category.setId(catId);

        expenseService.findAllWithCategory(category)
                .forEach(expense -> {
                    log.info("expense category: {}", expense);
                    expense.setCategory(category);
                    expenseService.save(expense);
                });

        this.categoryService.save(category);
        return "redirect:/categories";
    }


    @RequestMapping(
            value = {"/categories"},
            method = {RequestMethod.POST}
    )
    public String addNewCategory(Category category, RedirectAttributes redirectAttributes) {
        log.info("Saving called for tag: {}", category);
        this.categoryService.save(category);
        return "redirect:/categories";
    }
}
