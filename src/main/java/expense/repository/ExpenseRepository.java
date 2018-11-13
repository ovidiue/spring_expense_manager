package expense.repository;

import expense.model.Category;
import expense.model.Expense;
import expense.model.Rate;
import expense.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAll();

    @Query("select e from Expense e where :tag member e.tags")
    List<Expense> findAllWhereTag(@Param("tag") Tag tag);

    @Query("select e from Expense e where e.category=:category")
    List<Expense> findAllWithCategory(@Param("category") Category category);

    @Query("select e from Expense e")
    List<ExpenseIdsTitles> getAllNamesWithIds();

    @Query("select e from Expense e where :rate member e.rates")
    List<Expense> findAllWithRate(@Param("rate") Rate rate);

    Expense findByRatesIs(Rate rate);

    @Query("select e from Expense e where id in :ids")
    List<Expense> findAllByIds(@Param("ids") List<Long> expIds);


}
