package expense.web.controller;

import expense.model.Tag;
import expense.service.ExpenseService;
import expense.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private ExpenseService expenseService;

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    public TagController() {
    }

    @RequestMapping({"/tags"})
    public String getTags(Model model) {
        logger.info("Fetching tags route");
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        return "tags-listing";
    }

    @RequestMapping({"tags/add"})
    public String addTag(Model model) {
        logger.info("Fetch add view");
        model.addAttribute("tag", new Tag());
        return "tag-add";
    }

    @RequestMapping(
            value = {"tags/delete/{tagId}"},
            method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteTag(@PathVariable(name = "tagId") Long tagId, RedirectAttributes redirectAttributes) {
        log.info("deleteTag called");
        log.info("Tag id to delete: {}", tagId);
        tagService.findById(tagId)
                .ifPresent(tag -> {
                    log.info("tag to delete: {}", tag);
                    expenseService.findAllThatHaveTag(tag).forEach(expense -> {
                        log.info("expense with tag: {}", expense);
                        expense.getTags().remove(tag);
                        expenseService.save(expense);
                    });
                    tagService.deleteTag(tag);
                });
        return "redirect:/tags";
    }

    @RequestMapping(
            value = {"/tags"},
            method = {RequestMethod.POST}
    )
    public String addNewTag(Tag tag, RedirectAttributes redirectAttributes) {
        logger.info("Saving called for tag: {}", tag);
        this.tagService.save(tag);
        return "redirect:/tags";
    }

}
