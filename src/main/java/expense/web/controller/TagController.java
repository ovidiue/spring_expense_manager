package expense.web.controller;

import expense.model.Tag;
import expense.service.ExpenseService;
import expense.service.TagService;
import lombok.extern.slf4j.Slf4j;
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


    public TagController() {
    }

    @RequestMapping({"/tags"})
    public String getTags(Model model) {
        log.info("Fetching tags route");
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        return "tags-listing";
    }

    @RequestMapping({"tags/add"})
    public String addTag(Model model) {
        log.info("Fetch add view");
        model.addAttribute("tag", new Tag());
        model.addAttribute("formAction", "/tags/save/");
        model.addAttribute("pageTitle", "Add Tag");
        return "tag-add";
    }

    @RequestMapping({"tags/edit/{tagId}"})
    public String editTag(@PathVariable Long tagId, Model model) {
        Tag tag = this.tagService.findById(tagId).get();
        model.addAttribute("tag", tag);
        model.addAttribute("formAction", "/tags/update");
        model.addAttribute("pageTitle", "Edit Tag " + tag.getName());
        return "tag-add";
    }

    @RequestMapping(
            value = {"tags/update"},
            method = {RequestMethod.POST})
    public String editCat(Tag tag, RedirectAttributes redirectAttributes) {
        this.tagService.save(tag);
        return "redirect:/tags";
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
            value = {"/tags/save"},
            method = {RequestMethod.POST}
    )
    public String addNewTag(Tag tag, RedirectAttributes redirectAttributes) {
        log.info("Saving called for tag: {}", tag);
        this.tagService.save(tag);
        return "redirect:/tags";
    }

}
