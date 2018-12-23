package expense.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ovidiu on 23-Dec-18.
 */
@Controller
@Slf4j
@RequestMapping("/dashboard")
public class DashboardController {

  @RequestMapping("")
  public String getDasboardRoute() {
    return "dashboard";
  }

}
