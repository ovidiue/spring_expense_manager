package expense.web.controller;

import expense.model.Expense;
import expense.model.Rate;
import expense.repository.ExpenseBasic;
import expense.service.ExpenseService;
import expense.service.RateService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ovidiu on 07-Oct-18.
 */
@Controller
@Slf4j
@RequestMapping("/rates")
public class RateController {

  @Autowired
  RateService rateService;
  @Autowired
  ExpenseService expenseService;

  @GetMapping(value = {"/{expId}", ""})
  public String getRatesByExpense(@PathVariable(required = false) Long expId, Model model,
      HttpServletRequest request) {
    log.info("RATE_FOR_EXP_ID {}", expId);
    String uri = request.getRequestURI();
    List<Rate> rates = uri.equalsIgnoreCase("/rates") ?
        this.rateService.findAll() :
        this.rateService.findByExpenseId(expId);
    log.info("RATE_FOR_EXP_ID {}", rates);
    model.addAttribute("rates", rates);
    return "rates-listing";
  }

  @RequestMapping({"/add", "/add/{expId}"})
  public String getAddRateRoute(@PathVariable(required = false) Long expId, Model model) {
    if (!model.containsAttribute("rate")) {
      model.addAttribute("rate", new Rate());
    }
    List<ExpenseBasic> expenses = expenseService.getAllBasic();
    log.info("rate expenses: {}", expenses);
    model.addAttribute("expenses", expenses);
    model.addAttribute("formAction", "/rates/save");
    if (expId != null) {
      model.addAttribute("preselectedExpenseId", expId);
    }
    return "rate-add";
  }

  @GetMapping("/edit/{rateId}")
  public String getEditRateRoute(@PathVariable Long rateId, Model model) {
    //Rate rate = this.rateService.findById(rateId).get();
    Rate rate = model.containsAttribute("rate") ?
        (Rate) model.asMap().get("rate") :
        this.rateService.findById(rateId).get();
    if (!model.containsAttribute("rate")) {
      model.addAttribute("rate", rate);
    }
    model.addAttribute("rateValue", rate.getAmount());
    List<ExpenseBasic> expenses = expenseService.getAllBasic();
    Expense previousSetExpense = this.expenseService.findExpenseByRate(rate);
    log.info("previousExpense: {}", previousSetExpense);
    model.addAttribute("expense", previousSetExpense);
    log.info("rate expenses: {}", expenses);
    model.addAttribute("expenses", expenses);
    model.addAttribute("formAction", "/rates/update");
    return "rate-add";
  }

  @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
  public String saveRate(@RequestParam(required = false) Long expId,
      @Valid Rate rate,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    log.info("rate: {}", rate);

    Expense expense = null;

    if (expId != null) {
      expense = this.expenseService.findById(expId).get();
    }

    if (result.hasErrors()) {
      log.info("result {}", result);
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.rate", result);
      redirectAttributes.addFlashAttribute("expense", expense);
      redirectAttributes.addFlashAttribute("rate", rate);
      return "redirect:/rates/add";
    }
    if (expense != null) {
      expense.addRate(rate);
      rate.setExpense(expense);
      this.expenseService.save(expense);
      this.rateService.save(rate);
    } else {
      this.rateService.save(rate);
    }
    Map<String, String> notification = new HashMap<String, String>() {{
      put("type", "success");
      put("text", "Successfully saved rate " + rate.getAmount());
    }};
    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/rates";
  }

  @RequestMapping(value = {"/update"}, method = {RequestMethod.POST})
  public String updateRate(@RequestParam(required = false) Long expId,
      @Valid Rate rate,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    log.info("UPDATE RATE: {}", rate);
    log.info("UPDATE EXP_ID: {}", expId);
    Expense newChosenExpense = null;
    if (expId != null) {
      newChosenExpense = this.expenseService.findById(expId).get();
    }
    log.info("NEW_CHOSEN__EXPENSE {}", newChosenExpense);
    if (result.hasErrors()) {
      log.info("in has errors newChosenExpense {}", newChosenExpense);
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.rate", result);
      redirectAttributes.addFlashAttribute("rate", rate);
      redirectAttributes.addFlashAttribute("expense", newChosenExpense);
      return "redirect:/rates/edit/" + rate.getId();
    }
    Rate initialRate = this.rateService.findById(rate.getId()).get();
    log.info("INITIAL_RATE {}", initialRate);

    Expense previousSetExpense = this.expenseService.findExpenseByRate(rate);
    log.info("PREVIOUSE_SET_EXPENSE {}", previousSetExpense);
    if (expId != null && previousSetExpense != null) {
      log.info("PREVIOUSE_SET_EXPENSE_ID {}", previousSetExpense.getId());
      log.info("EXP_ID_NOT_NULL: {}", expId);
      if (expId.equals(previousSetExpense.getId())) {
        log.info("IN_EQUAL_IDS");
        previousSetExpense.removeRate(initialRate);
        previousSetExpense.addRate(rate);
        this.expenseService.save(previousSetExpense);
        rate.setExpense(previousSetExpense);
        this.rateService.save(rate);
      } else if (!expId.equals(previousSetExpense.getId())) {
        log.info("IN_DIFFERENT_IDS");
        previousSetExpense.removeRate(initialRate);
        this.expenseService.save(previousSetExpense);
        newChosenExpense.addRate(rate);
        rate.setExpense(newChosenExpense);
        this.expenseService.save(newChosenExpense);
        this.rateService.save(rate);
      }
    } else if (expId != null && previousSetExpense == null) {
      log.info("IN EXP_ID NOT NULL AND PREVIVOUSE_SET_EXPENSE NULL");
      newChosenExpense.addRate(rate);
      rate.setExpense(newChosenExpense);
      this.expenseService.save(newChosenExpense);
      this.rateService.save(rate);
    } else {
      log.info("EXP_ID_IS_NULL");
      if (previousSetExpense != null) {
        previousSetExpense.removeRate(initialRate);
        this.expenseService.save(previousSetExpense);
      }
      rate.setExpense(null);
      this.rateService.save(rate);
    }
    Map<String, String> notification = new HashMap<String, String>() {{
      put("type", "success");
      put("text", "Successfully updated rate " + rate.getAmount());
    }};
    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/rates";
  }

  @RequestMapping(value = "/delete/{rateId}",
      method = {RequestMethod.POST, RequestMethod.GET})
  public String deleteRate(@PathVariable Long rateId, RedirectAttributes redirectAttributes) {
    this.rateService.findById(rateId)
        .ifPresent(r -> {
          List<Expense> expensesWithRate = this.expenseService.findAllWithRate(r);
          expensesWithRate.forEach(ex -> {
            log.info("expense that has rate {}", ex);
            ex.removeRate(r);
            log.info("expense after calculation {}", ex);
            this.expenseService.save(ex);
          });

          log.info("delete rate {}", r);
          if (r.getExpense() != null) {
            r.setExpense(null);
          }

          this.rateService.delete(r);

          Map<String, String> notification = new HashMap<String, String>() {{
            put("type", "success");
            put("text", "Successfully deleted rate " + r.getAmount());
          }};
          redirectAttributes.addFlashAttribute("notification", notification);
        });

    return "redirect:/rates";
  }

}
