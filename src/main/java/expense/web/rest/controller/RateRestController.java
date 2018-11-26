package expense.web.rest.controller;

import expense.model.Rate;
import expense.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ovidiu on 25-Nov-18.
 */
@RequestMapping(value = "/api/rates/")
@RestController
public class RateRestController {
    @Autowired
    private RateService rateService;

    @GetMapping("get-all")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Rate> getAllRates() {
        return this.rateService.findAll();
    }

    @GetMapping("get-all/{expId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Rate> getAllRatesForExpense(@PathVariable Long expId) {
        return this.rateService.findByExpenseId(expId);
    }
}
