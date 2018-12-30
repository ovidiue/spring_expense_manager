package expense.web.controller;

import expense.model.dashboard.CategoryStats;
import expense.model.dashboard.ExpenseSimplified;
import expense.model.dashboard.ExpenseStats;
import expense.service.CategoryService;
import expense.service.ExpenseService;
import expense.service.TagService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ovidiu on 23-Dec-18.
 */
@Controller
@Slf4j
@RequestMapping("/dashboard")
public class DashboardController {

  @Autowired
  private CategoryService categoryService;
  @Autowired
  private TagService tagService;
  @Autowired
  private ExpenseService expenseService;

  @RequestMapping("")
  public String getDasboardRoute(Model model) {
    Map<String, Integer> categoryCount = this.categoryService.getCategoryCount();
    model.addAttribute("categoryCount", categoryCount);
    List<CategoryStats> categoryStats = this.categoryService.getCategoryInfo();
    model.addAttribute("categoryStats", categoryStats);
    List<CategoryStats> tagStats = this.tagService.getTagInfo();
    model.addAttribute("tagStats", tagStats);
    List<ExpenseSimplified> simpleExp = this.expenseService.findAllSimple();
    model.addAttribute("expenses", simpleExp);
    ExpenseStats exInfo = this.expenseService.getStatsInfo();
    model.addAttribute("expenseInfo", exInfo);
    return "dashboard";
  }

}
