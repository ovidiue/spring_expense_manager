package expense.repository;

import expense.model.Rate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

  List<Rate> findAllByExpense_Id(@Param("expId") Long expId);
}
