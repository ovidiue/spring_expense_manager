package expense.service;

import expense.model.Expense;
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
        return (List<Expense>) expenseRepository.findAll();
    }
}
