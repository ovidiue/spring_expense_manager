package expense.service;

import expense.model.Expense;
import expense.model.Tag;
import expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public void save(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> findAllThatHaveTag(Tag tag) {
        return expenseRepository.findAllWhereTag(tag);
    }
}
