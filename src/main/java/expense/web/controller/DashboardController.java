package expense.web.controller;

import expense.repository.CategoryStats;
import expense.service.CategoryService;
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


  @RequestMapping("")
  public String getDasboardRoute(Model model) {
    Map<String, Integer> categoryCount = this.categoryService.getCategoryCount();
    model.addAttribute("categoryCount", categoryCount);
    List<CategoryStats> categoryStats = this.categoryService.getCategoryInfo();
    model.addAttribute("categoryStats", categoryStats);
    log.info("categoryStats {}", categoryStats);
    return "dashboard";
  }


}
