package expense.web.controller;

import expense.model.Category;
import expense.service.CategoryService;
import expense.service.ExpenseService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
@Slf4j
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ExpenseService expenseService;

  public CategoryController() {
  }

  @GetMapping("")
  public String getCategories(Model model) {
    List<Category> categories = categoryService.findAll();
    model.addAttribute("categories", categories);
    return "categories-listing";
  }

  @RequestMapping({"/add"})
  public String getAddCatRoute(Model model) {
    log.info("Fetch add view");
    if (!model.containsAttribute("category")) {
      model.addAttribute("category", new Category());
    }
    model.addAttribute("formAction", "/categories/save");
    model.addAttribute("pageTitle", "Add Category");
    return "category-add";
  }

  @RequestMapping({"/edit/{catId}"})
  public String editCategoryRoute(@PathVariable Long catId, Model model) {
    log.info("Fetch edit view");
    Category category = this.categoryService.findById(catId).get();
    if (!model.containsAttribute("category")) {
      model.addAttribute("category", category);
    }
    model.addAttribute("formAction", "/categories/update");
    model.addAttribute("pageTitle", "Edit Category " + category.getName());
    return "category-add";
  }

  @RequestMapping(
      value = {"/delete/{catId}"},
      method = {RequestMethod.POST, RequestMethod.GET})
  public String deleteCat(@PathVariable(name = "catId") Long catId,
      RedirectAttributes redirectAttributes) {
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
          Map<String, String> notification = new HashMap<String, String>() {{
            put("type", "success");
            put("text", "Successfully deleted category " + category.getName());
          }};
          redirectAttributes.addFlashAttribute("notification", notification);
        });
    return "redirect:/categories";
  }

  @RequestMapping(
      value = {"/update"},
      method = {RequestMethod.POST})
  public String editCat(@Valid Category category,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.category", result);
      redirectAttributes.addFlashAttribute("category", category);
      return "redirect:/categories/edit/" + category.getId();
    }
    this.categoryService.save(category);
    Map<String, String> notification = new HashMap<String, String>() {{
      put("type", "success");
      put("text", "Successfully updated category " + category.getName());
    }};
    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/categories";
  }


  @RequestMapping(
      value = {"/save"},
      method = {RequestMethod.POST}
  )
  public String saveNewCategory(@Valid Category category,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    log.info("Saving called for tag: {}", category);
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.category", result);
      redirectAttributes.addFlashAttribute("category", category);
      return "redirect:/categories/add";
    }
    this.categoryService.save(category);
    Map<String, String> notification = new HashMap<String, String>() {{
      put("type", "success");
      put("text", "Successfully saved category " + category.getName());
    }};
    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/categories";
  }
}
