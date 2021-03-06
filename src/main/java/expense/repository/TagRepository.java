package expense.repository;

import expense.model.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Ovidiu on 04-Oct-18.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  @Query("select t from Tag t where id in :ids")
  List<Tag> findAllByIds(@Param("ids") List<Long> tagIds);

  Tag findByName(String name);
}
