package expense.service;

import expense.model.Category;
import expense.model.Expense;
import expense.model.Rate;
import expense.model.Tag;
import expense.repository.ExpenseIdsTitles;
import expense.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
@Slf4j
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

    public List<Expense> findAllWithCategory(Category category) {
        return expenseRepository.findAllWithCategory(category);
    }

    public Optional<Expense> findById(Long id) {
        return this.expenseRepository.findById(id);
    }

    public void deleteExpense(Expense expense) {
        this.expenseRepository.delete(expense);
    }

    public List<ExpenseIdsTitles> getExpensesNames() {
        return this.expenseRepository.getAllNamesWithIds();
    }

    public List<Expense> findAllWithRate(Rate rate) {
        return this.expenseRepository.findAllWithRate(rate);
    }

    public List<Expense> findAllByIds(List<Long> ids) {
        return this.expenseRepository.findAllByIds(ids);
    }
}
