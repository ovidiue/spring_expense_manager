package expense.service;

import expense.model.Rate;
import expense.repository.RateRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class RateService {

  @Autowired
  private RateRepository rateRepository;

  public List<Rate> findAll() {
    return rateRepository.findAll();
  }

  public void save(Rate rate) {
    this.rateRepository.save(rate);
  }

  public Optional<Rate> findById(Long id) {
    return this.rateRepository.findById(id);
  }

  public void delete(Rate r) {
    this.rateRepository.delete(r);
  }

  public List<Rate> findByExpenseId(Long expId) {
    return this.rateRepository.findAllByExpense_Id(expId);
  }
}
