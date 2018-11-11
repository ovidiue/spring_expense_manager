package expense.web.controller;

import expense.model.Rate;
import expense.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Ovidiu on 07-Oct-18.
 */
@Controller
public class RateController {
    @Autowired
    RateService rateService;
    Logger logger = LoggerFactory.getLogger(RateController.class);

    @GetMapping("/rates")
    public String getRates(Model model) {
        logger.info("Access index");
        List<Rate> rates = rateService.findAll();
        logger.info("Fetch rates");
        model.addAttribute("rates", rates);
        logger.info("Add rates on model {}", rates);
        return "rates-listing";
    }

}
