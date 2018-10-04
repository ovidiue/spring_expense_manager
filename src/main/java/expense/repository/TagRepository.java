package expense.repository;

import expense.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Ovidiu on 04-Oct-18.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAll();
}
