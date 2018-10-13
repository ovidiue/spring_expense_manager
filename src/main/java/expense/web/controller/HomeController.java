package expense.web.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Ovidiu on 14-Oct-18.
 */
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

}
