package expense.service;

import expense.model.Category;
import expense.model.Expense;
import expense.model.Rate;
import expense.model.Tag;
import expense.repository.ExpenseIdsTitles;
import expense.repository.ExpenseRepository;
import expense.web.controller.ExpenseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
@Slf4j
public class ExpenseService {
    @PersistenceContext
    EntityManager em;
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

    public Expense findExpenseByRate(Rate rate) {
        return this.expenseRepository.findByRatesIs(rate);
    }

    public List<Expense> findAllByIds(List<Long> ids) {
        return this.expenseRepository.findAllByIds(ids);
    }

    public List<Expense> findAll(ExpenseFilter filter) {
        log.info("findAll with filter called {}", filter);
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<Expense> query = criteriaBuilder.createQuery(Expense.class);
        Root<Expense> r = query.from(Expense.class);
        query.select(r);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTitle() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            r.get("title"), "%" + filter.getTitle() + "%"
                    ));
        }

        if (filter.getDueDateFrom() != null && filter.getDueDateTo() != null) {
            predicates.add(
                    criteriaBuilder.between(
                            r.get("dueDate"), filter.getDueDateFrom(), filter.getDueDateTo()
                    )
            );
        }

        if (filter.getCreatedFrom() != null && filter.getCreatedTo() != null) {
            predicates.add(
                    criteriaBuilder.between(
                            r.get("createdOn"), filter.getCreatedFrom(), filter.getCreatedTo()
                    )
            );
        }

        if (filter.getDescription() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            r.get("description"), "%" + filter.getDescription() + "%")
            );
        }

        if (filter.isRecurrent()) {
            predicates.add(
                    criteriaBuilder.equal(
                            r.get("recurrent"), filter.isRecurrent()
                    )
            );
        }

        if (filter.getAmountFrom() != null && filter.getAmountTo() != null) {
            predicates.add(
                    criteriaBuilder.between(
                            r.get("amount"), filter.getAmountFrom(), filter.getAmountTo()
                    )
            );
        }

        if (filter.getCategoryId() != null) {
            predicates.add(
                    criteriaBuilder.equal(
                            r.get("category").get("id"), filter.getCategoryId()
                    )
            );
        }

        query.select(r).where(predicates.toArray(new Predicate[]{}));


        return this.em.createQuery(query).getResultList();
    }


}
