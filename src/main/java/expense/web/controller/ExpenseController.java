package expense.web.controller;

import expense.model.Expense;
import expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public String getExpenses(Model model) {
        List<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "expenses-listing";
    }

}
