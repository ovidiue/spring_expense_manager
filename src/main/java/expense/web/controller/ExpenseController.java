package expense.web.controller;

import expense.model.Expense;
import expense.service.CategoryService;
import expense.service.ExpenseService;
import expense.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
@Slf4j
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @GetMapping("/expenses")
    public String getExpenses(Model model) {
        List<Expense> expenses = expenseService.findAll();
        expenses.forEach(expense -> {
            log.info("\nexpense: {}\n", expense);
        });
        model.addAttribute("expenses", expenses);
        return "expenses-listing";
    }

    @RequestMapping({"expenses/add"})
    public String addExpense(Model model) {
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("expense", new Expense());
        return "expense-add";
    }

    @RequestMapping(value = {"/expenses"}, method = {RequestMethod.POST})
    public String addNewExpense(@RequestParam(name = "categoryId") Long categoryId,
                                @RequestParam(name = "tagsIds") List<Long> tagIds,
                                Expense expense,
                                RedirectAttributes redirectAttributes) {
        log.info("categoryId: {}", categoryId);
        log.info("tagsIds: {}", tagIds);

        categoryService.findById(categoryId)
                .ifPresent(category -> {
                    log.info("CATEGORY FOUND: {}", category);
                    expense.setCategory(category);
                });
        tagService.findAllByIds(tagIds)
                .ifPresent(tags -> {
                    expense.setTags(tags);
                });
        log.info("EXPENSE TO SAVE: {}", expense);
        expense.setCreatedOn(new Date());
        expenseService.save(expense);
        return "redirect:/expenses";
    }

}
