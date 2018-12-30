package expense.service;

import expense.model.Category;
import expense.model.Expense;
import expense.model.Tag;
import expense.model.dashboard.CategoryStats;
import expense.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class TagService {

  @Autowired
  private TagRepository tagRepository;
  @Autowired
  private ExpenseService expenseService;

  public void deleteTag(Tag tag) {
    tagRepository.delete(tag);
  }

  public List<Tag> findAll() {
    return tagRepository.findAll();
  }

  public Tag save(Tag tag) {
    return tagRepository.save(tag);
  }

  public Optional<List<Tag>> findAllByIds(List<Long> ids) {
    return Optional.ofNullable(this.tagRepository.findAllByIds(ids));
  }

  public boolean tagNameExists(String name) {
    return this.tagRepository.findByName(name) != null;
  }

  public Optional<Tag> findById(Long id) {
    return tagRepository.findById(id);
  }

  public List<CategoryStats> getTagInfo() {
    List<CategoryStats> result = new ArrayList();
    List<Tag> tags = this.findAll();

    tags.forEach(tag -> {
      List<Expense> expenses = this.expenseService.findAllThatHaveTag(tag);

      double total = expenses.stream().mapToDouble(e -> e.getAmount()).sum();
      double totalPayed = expenses.stream().mapToDouble(e -> e.getPayed()).sum();
      double min = expenses.stream().mapToDouble(e -> e.getAmount()).min().orElse(0);
      double max = expenses.stream().mapToDouble(e -> e.getAmount()).max().orElse(0);
      long recurrentCount = expenses.stream().filter(ex -> ex.isRecurrent() == true).count();
      long nonRecurrentCount = expenses.stream().filter(ex -> ex.isRecurrent() == false).count();
      long noOfExpenses = expenses.stream().count();
      long closed = expenses.stream().filter(ex -> {
        return Double.compare(ex.getPayed(), ex.getAmount()) == 0;
      }).count();

      result.add(new CategoryStats(tag.getName(),
          tag.getColor(),
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
