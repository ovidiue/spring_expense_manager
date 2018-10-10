package expense.repository;

import expense.model.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
