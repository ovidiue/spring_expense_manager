package expense.repository;

import expense.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ovidiu on 04-Oct-18.
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAll();

    @Query("select t from Tag t where id in :ids")
    List<Tag> findAllByIds(@Param("ids") List<Long> tagIds);
}
