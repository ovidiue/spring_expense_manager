package expense.repository;

import expense.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
