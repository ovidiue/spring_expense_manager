package expense.web.controller;

import expense.model.Expense;
import expense.service.CategoryService;
import expense.service.ExpenseService;
import expense.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping("expenses/edit/{expId}")
    public String editExpense(@PathVariable("expId") Long expId, Model model) {
        log.info("expense edit screen fetched");
        Expense expense = this.expenseService.findById(expId).get();
        model.addAttribute("expense", expense);
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        return "expense-edit";
    }

    @RequestMapping("expenses/edit/{expId}/update")
    public String updateEditedExpense(@PathVariable Long expId,
                                      @RequestParam Long categoryId,
                                      @RequestParam(required = false) List<Long> tagsIds,
                                      Expense expense,
                                      RedirectAttributes redirectAttributes) {
        expense.setId(expId);
        this.categoryService.findById(categoryId)
                .ifPresent(category -> {
                    expense.setCategory(category);
                });
        if (tagsIds != null) {
            this.tagService.findAllByIds(tagsIds)
                    .ifPresent(tags -> {
                        expense.setTags(tags);
                    });
        } else {
            expense.setTags(null);
        }

        log.debug("expense to update: {}", expense);

        this.expenseService.save(expense);

        return "redirect:/expenses";
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

    @RequestMapping(
            value = {"expenses/delete/{expId}"},
            method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteExp(@PathVariable(name = "expId") Long expId, RedirectAttributes redirectAttributes) {
        log.info("deleteExp called");
        log.info("Exp id to delete: {}", expId);
        expenseService.findById(expId)
                .ifPresent(expense -> {
                    log.info("expense to delete: {}", expense);
                    expenseService.deleteExpense(expense);
                });
        return "redirect:/expenses";
    }

}
