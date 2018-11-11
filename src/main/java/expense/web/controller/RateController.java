package expense.web.controller;

import expense.model.Expense;
import expense.model.Rate;
import expense.repository.ExpenseIdsTitles;
import expense.service.ExpenseService;
import expense.service.RateService;
import lombok.extern.slf4j.Slf4j;
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
    @Autowired
    ExpenseService expenseService;

    @GetMapping("/rates")
    public String getRates(Model model) {
        log.info("Access index");
        List<Rate> rates = rateService.findAll();
        log.info("Fetch rates");
        model.addAttribute("rates", rates);
        log.info("Add rates on model {}", rates);
        return "rates-listing";
    }

    @RequestMapping("/rates/add")
    public String getAddRateView(Model model) {
        model.addAttribute("rate", new Rate());
        List<ExpenseIdsTitles> expenses = expenseService.getExpensesNames();
        log.info("rate expenses: {}", expenses);
        model.addAttribute("expenses", expenses);
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

    @RequestMapping(value = "rates/delete/{rateId}",
            method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteRate(@PathVariable Long rateId, RedirectAttributes redirectAttributes) {
        this.rateService.findById(rateId)
                .ifPresent(r -> {
                    List<Expense> expensesWithRate = this.expenseService.findAllWithRate(r);
                    expensesWithRate.forEach(ex -> {
                        ex.getRates().remove(r);
                        this.expenseService.save(ex);
                    });
                    this.rateService.delete(r);
                });
        return "redirect:/rates";
    }

}
