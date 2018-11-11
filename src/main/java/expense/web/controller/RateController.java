package expense.web.controller;

import expense.model.Rate;
import expense.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Ovidiu on 07-Oct-18.
 */
@Controller
@Slf4j
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

    @RequestMapping("/rates/add")
    public String getAddRateView(Model model) {
        model.addAttribute("rate", new Rate());
        return "rate-add";
    }

    @GetMapping("/rates/edit/{rateId}")
    public String getEditRateView(@PathVariable Long rateId, Model model) {
        Rate rate = this.rateService.findById(rateId).get();
        model.addAttribute("rate", rate);
        return "rate-add";
    }


    @RequestMapping(value = {"/rates/save"}, method = {RequestMethod.POST})
    public String saveRate(Rate rate, RedirectAttributes redirectAttributes) {
        log.info("rate: {}", rate);
        this.rateService.save(rate);
        return "redirect:/rates";
    }

}
