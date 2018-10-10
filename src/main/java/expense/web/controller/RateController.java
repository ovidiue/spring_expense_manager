package expense.web.controller;

import expense.model.Tag;
import expense.repository.TagRepository;
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
    TagRepository tagRepository;
    Logger logger = LoggerFactory.getLogger(RateController.class);

    public RateController() {
    }

    @GetMapping("/")
    public String getRates(Model model) {
        logger.info("Access index");
        List<Tag> rates = tagRepository.findAll();
        logger.info("Fetch tags");
        model.addAttribute("rates", rates);
        logger.info("Add rates on model {}", rates);
        return "index.html";
    }

}
