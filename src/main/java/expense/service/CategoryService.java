package expense.service;

import expense.model.Category;
import expense.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

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
}
