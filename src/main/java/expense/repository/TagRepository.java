package expense.repository;

import expense.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ovidiu on 04-Oct-18.
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAll();
}
