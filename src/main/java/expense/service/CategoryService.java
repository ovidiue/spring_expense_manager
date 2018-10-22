package expense.service;

import expense.model.Category;
import expense.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public void save(Category category) {
        this.categoryRepository.save(category);
    }

    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }
}
