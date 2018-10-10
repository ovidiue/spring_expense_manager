package expense.repository;

import expense.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
