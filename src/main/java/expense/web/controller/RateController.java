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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
        if (!model.containsAttribute("rate")) {
            model.addAttribute("rate", new Rate());
        }
        List<ExpenseIdsTitles> expenses = expenseService.getExpensesNames();
        log.info("rate expenses: {}", expenses);
        model.addAttribute("expenses", expenses);
        model.addAttribute("formAction", "/rates/save");
        return "rate-add";
    }

    @GetMapping("/rates/edit/{rateId}")
    public String getEditRateView(@PathVariable Long rateId, Model model) {
        //Rate rate = this.rateService.findById(rateId).get();
        Rate rate = model.containsAttribute("rate") ?
                (Rate) model.asMap().get("rate") :
                this.rateService.findById(rateId).get();
        if (!model.containsAttribute("rate")) {
            model.addAttribute("rate", rate);
        }
        List<ExpenseIdsTitles> expenses = expenseService.getExpensesNames();
        Expense previousSetExpense = this.expenseService.findExpenseByRate(rate);
        log.info("previousExpense: {}", previousSetExpense);
        model.addAttribute("expense", previousSetExpense);
        log.info("rate expenses: {}", expenses);
        model.addAttribute("expenses", expenses);
        model.addAttribute("formAction", "/rates/update");
        return "rate-add";
    }

    @RequestMapping(value = {"/rates/save"}, method = {RequestMethod.POST})
    public String saveRate(@RequestParam(required = false) Long expId,
                           @Valid Rate rate,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        log.info("rate: {}", rate);
        if (result.hasErrors()) {
            log.info("result {}", result);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.rate", result);
            redirectAttributes.addFlashAttribute("rate", rate);
            return "redirect:/rates/add";
        }
        if (expId != null) {
            this.expenseService.findById(expId)
                    .ifPresent(expense -> {
                        log.info("rate chosen expense: {}", expense);
                        expense.getRates().add(rate);
                        this.expenseService.save(expense);
                    });
        } else {
            this.rateService.save(rate);
        }

        return "redirect:/rates";
    }

    @RequestMapping(value = {"/rates/update"}, method = {RequestMethod.POST})
    public String updateRate(@RequestParam(required = false) Long expId,
                             @Valid Rate rate,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        log.info("rate: {}", rate);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.rate", result);
            redirectAttributes.addFlashAttribute("rate", rate);
            return "redirect:/rates/add";
        }
        Rate initialRate = this.rateService.findById(rate.getId()).get();

        Expense previousSetExpense = this.expenseService.findExpenseByRate(rate);
        log.info("previousSetExpense: {}", previousSetExpense);
        if (expId != null && previousSetExpense != null) {
            log.info("expId not null: {}", expId);
            if (expId == previousSetExpense.getId()) {
                log.info("in equal ids");
                this.rateService.save(rate);
            } else if (expId != previousSetExpense.getId()) {
                log.info("in different ids");
                previousSetExpense.getRates().remove(initialRate);
                this.expenseService.save(previousSetExpense);
                Expense newChosenExpense = this.expenseService.findById(expId).get();
                newChosenExpense.getRates().add(rate);
                this.expenseService.save(newChosenExpense);
            }
        } else if (expId != null && previousSetExpense == null) {
            this.expenseService.findById(expId)
                    .ifPresent(expense -> {
                        expense.getRates().add(rate);
                        this.expenseService.save(expense);
                    });
        } else {
            log.info("expId is null");
            if (previousSetExpense != null) {
                previousSetExpense.getRates().remove(initialRate);
                this.expenseService.save(previousSetExpense);
            }
            this.rateService.save(rate);
        }

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
