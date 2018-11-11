package expense.service;

import expense.model.Rate;
import expense.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
