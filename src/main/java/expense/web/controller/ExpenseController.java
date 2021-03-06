package expense.web.controller;

import expense.model.Expense;
import expense.model.Rate;
import expense.service.CategoryService;
import expense.service.ExpenseService;
import expense.service.RateService;
import expense.service.TagService;
import expense.utils.Notification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
@Slf4j
@Transactional
@RequestMapping("/expenses")
public class ExpenseController {

  private final String NAME_EXISTS = " already exists. Please choose another title";
  @Autowired
  private ExpenseService expenseService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private TagService tagService;
  @Autowired
  private RateService rateService;

  @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST},
      value = "")
  public String getExpenses(ExpenseFilter filter, Model model) {
    log.info("filter {}", filter);
    log.info("model {}", model);

    List<Expense> expenses;

    if (filter != null) {
      expenses = this.expenseService.findAll(filter);
    } else {
      expenses = this.expenseService.findAll();
    }
    // TODO just fetch tags id and title
    // TODO refactor into map of pageparams
    model.addAttribute("tags", this.tagService.findAll());
    model.addAttribute("isFilterEmpty", filter.isEmpty());
    model.addAttribute("categories", this.categoryService.findAll());
    model.addAttribute("expenses", expenses);
    model.addAttribute("filter", filter == null ? new ExpenseFilter() : filter);

    return "expenses-listing";
  }

  @RequestMapping("/edit/{expId}")
  public String getEditExpenseRoute(@PathVariable("expId") Long expId, Model model) {
    log.info("expense edit screen fetched");
    Expense expense = this.expenseService.findById(expId).get();
    if (!model.containsAttribute("expense")) {
      model.addAttribute("expense", expense);
    }
    model.addAttribute("pageTitle", "Edit Expense " + expense.getTitle());
    model.addAttribute("formAction", "/expenses/update");
    model.addAttribute("tags", tagService.findAll());
    model.addAttribute("categories", categoryService.findAll());

    return "expense-add";
  }

  @RequestMapping("/update")
  public String updateEditedExpense(@RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) List<Long> tagsIds,
      @Valid Expense expense,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    if (categoryId != null) {
      this.categoryService.findById(categoryId)
          .ifPresent(category -> {
            expense.setCategory(category);
          });
    }

    if (tagsIds != null) {
      this.tagService.findAllByIds(tagsIds)
          .ifPresent(tags -> {
            expense.setTags(tags);
          });
    }

    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.expense", result);
      redirectAttributes.addFlashAttribute("expense", expense);
      redirectAttributes.addFlashAttribute("categoryId", categoryId);
      redirectAttributes.addFlashAttribute("tagsIds", tagsIds);
      return "redirect:/expenses/edit/" + expense.getId();
    }

    Expense initialExpense = this.expenseService.findById(expense.getId()).get();
    if (!expense.getTitle().equalsIgnoreCase(initialExpense.getTitle()) && this.expenseService
        .nameExists(expense.getTitle())) {
      redirectAttributes.addFlashAttribute("expense", expense);
      redirectAttributes.addFlashAttribute("notification",
          Notification.build("error", expense.getTitle() + this.NAME_EXISTS));
      return "redirect:/expenses/edit/" + expense.getId();
    }

    log.info("expense to update: {}", expense);

    this.expenseService.save(expense);
    Map<String, String> notification = new HashMap<String, String>() {{
      put("type", "success");
      put("text", "Successfully updated expense " + expense.getTitle());
    }};
    redirectAttributes.addFlashAttribute("notification", notification);

    return "redirect:/expenses";
  }

  @RequestMapping({"/add"})
  public String getAddExpenseRoute(Model model) {
    if (!model.containsAttribute("expense")) {
      model.addAttribute("expense", new Expense());
    }
    model.addAttribute("tags", tagService.findAll());
    model.addAttribute("categories", categoryService.findAll());
    model.addAttribute("pageTitle", "Add Expense");
    model.addAttribute("formAction", "/expenses/save");

    return "expense-add";
  }

  @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
  public String saveNewExpense(@RequestParam(name = "categoryId", required = false) Long categoryId,
      @RequestParam(name = "tagsIds", required = false) List<Long> tagIds,
      @Valid Expense expense,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    log.info("categoryId: {}", categoryId);
    log.info("tagsIds: {}", tagIds);

    if (categoryId != null) {
      categoryService.findById(categoryId)
          .ifPresent(category -> {
            log.info("CATEGORY FOUND: {}", category);
            expense.setCategory(category);
          });
    }
    if (tagIds != null) {
      tagService.findAllByIds(tagIds)
          .ifPresent(tags -> {
            expense.setTags(tags);
          });
    }

    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.expense", result);
      redirectAttributes.addFlashAttribute("expense", expense);
      redirectAttributes.addFlashAttribute("categoryId", categoryId);
      redirectAttributes.addFlashAttribute("tagIds", tagIds);
      return "redirect:/expenses/add";
    }

    if (this.expenseService.nameExists(expense.getTitle())) {
      redirectAttributes.addFlashAttribute("expense", expense);
      redirectAttributes.addFlashAttribute("notification",
          Notification.build("error", expense.getTitle() + this.NAME_EXISTS));
      return "redirect:/expenses/add";
    }

    log.info("EXPENSE TO SAVE: {}", expense);

    expenseService.save(expense);
    Map<String, String> notification = new HashMap<String, String>() {{
      put("type", "success");
      put("text", "Successfully saved expense " + expense.getTitle());
    }};
    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/expenses";
  }

  @RequestMapping(
      value = {"/delete/{expId}"},
      method = {RequestMethod.POST, RequestMethod.GET})
  public String deleteExp(@PathVariable(name = "expId") Long expId,
      RedirectAttributes redirectAttributes) {
    log.info("deleteExp called");
    log.info("Exp id to delete: {}", expId);
    expenseService.findById(expId)
        .ifPresent(expense -> {
          log.info("expense to delete: {}", expense);
          expenseService.deleteExpense(expense);
          Map<String, String> notification = new HashMap<String, String>() {{
            put("type", "success");
            put("text", "Successfully deleted expense " + expense.getTitle());
          }};
          redirectAttributes.addFlashAttribute("notification", notification);
        });

    return "redirect:/expenses";
  }

  @RequestMapping(
      value = {"/delete-rates/{expId}"},
      method = {RequestMethod.POST, RequestMethod.GET})
  public String deleteExpAndRates(@PathVariable(name = "expId") Long expId,
      RedirectAttributes redirectAttributes) {
    log.info("deleteExpRates called");
    log.info("Exp id to delete: {}", expId);

    expenseService.findById(expId)
        .ifPresent(expense -> {
          log.info("expense to delete: {}", expense);
          List<Rate> rates = expense.getRates();
          rates.forEach(r -> {
            this.rateService.delete(r);
          });
          expenseService.deleteExpense(expense);
          Map<String, String> notification = new HashMap<String, String>() {{
            put("type", "success");
            put("text", "Successfully deleted expense " + expense.getTitle() + " and its rates");
          }};
          redirectAttributes.addFlashAttribute("notification", notification);
        });

    return "redirect:/expenses";
  }

}
