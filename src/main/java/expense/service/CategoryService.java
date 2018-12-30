package expense.service;

import expense.model.Category;
import expense.model.Expense;
import expense.repository.CategoryRepository;
import expense.model.dashboard.CategoryStats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
@Slf4j
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ExpenseService expenseService;

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public void save(Category category) {
    this.categoryRepository.save(category);
  }

  public Optional<Category> findById(Long id) {
    return this.categoryRepository.findById(id);
  }

  public void deleteCategory(Category category) {
    this.categoryRepository.delete(category);
  }

  public boolean categoryNameExists(String name) {
    return this.categoryRepository.findByName(name) != null;
  }

  public Map<String, Integer> getCategoryCount() {
    Map<String, Integer> result = new HashMap<>();
    List<Category> categories = this.findAll();

    categories.forEach(cat -> {
      result.put(cat.getName(), this.expenseService.countWithCategory(cat));
    });

    return result;
  }

  public List<CategoryStats> getCategoryInfo() {
    List result = new ArrayList();
    List<Category> categories = this.findAll();

    categories.forEach(cat -> {
      List<Expense> expenses = this.expenseService.findAllWithCategory(cat);

      double total = expenses.stream().mapToDouble(e -> e.getAmount()).sum();
      double totalPayed = expenses.stream().mapToDouble(e -> e.getPayed()).sum();
      double min = expenses.stream().mapToDouble(e -> e.getAmount()).min().orElse(0);
      double max = expenses.stream().mapToDouble(e -> e.getAmount()).max().orElse(0);
      long recurrentCount = expenses.stream().filter(ex -> ex.isRecurrent() == true).count();
      long nonRecurrentCount = expenses.stream().filter(ex -> ex.isRecurrent() == false).count();
      long noOfExpenses = expenses.stream().count();
      long closed = expenses.stream().filter(ex -> {
        log.info("ex {}", ex);
        return Double.compare(ex.getPayed(), ex.getAmount()) == 0;
      }).count();

      result.add(new CategoryStats(cat.getName(),
          cat.getColor(),
          totalPayed,
          total,
          min,
          max,
          recurrentCount,
          nonRecurrentCount,
          closed,
          noOfExpenses));

    });

    return result;
  }
}
