package expense.web.controller;

import expense.model.Category;
import expense.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private Logger logger = LoggerFactory.getLogger(TagController.class);

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
        logger.info("Fetch add view");
        model.addAttribute("category", new Category());
        return "category-add";
    }

    @RequestMapping(
            value = {"/categories"},
            method = {RequestMethod.POST}
    )
    public String addNewCategory(Category category, RedirectAttributes redirectAttributes) {
        logger.info("Saving called for tag: {}", category);
        this.categoryService.save(category);
        return "redirect:/categories";
    }
}
