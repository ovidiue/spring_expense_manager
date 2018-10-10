package expense.service;

import expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
}
