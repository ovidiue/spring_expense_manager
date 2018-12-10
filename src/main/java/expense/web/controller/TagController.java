package expense.web.controller;

import expense.model.Tag;
import expense.service.ExpenseService;
import expense.service.TagService;
import expense.utils.Notification;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
@Slf4j
@RequestMapping("/tags")
public class TagController {

  @Autowired
  private TagService tagService;
  @Autowired
  private ExpenseService expenseService;


  public TagController() {
  }

  @RequestMapping({""})
  public String getTags(Model model) {
    log.info("Fetching tags route");
    List<Tag> tags = tagService.findAll();
    model.addAttribute("tags", tags);
    return "tags-listing";
  }

  @RequestMapping({"/add"})
  public String addTagRoute(Model model) {
    log.info("add tag route called");
    if (!model.containsAttribute("tag")) {
      model.addAttribute("tag", new Tag());
    }
    model.addAttribute("formAction", "/tags/save/");
    model.addAttribute("pageTitle", "Add Tag");
    return "tag-add";
  }

  @RequestMapping({"/edit/{tagId}"})
  public String editTagRoute(@PathVariable Long tagId, Model model) {
    log.info("edit tag route called");
    Tag tag = this.tagService.findById(tagId).get();
    if (!model.containsAttribute("tag")) {
      model.addAttribute("tag", tag);
    }
    model.addAttribute("formAction", "/tags/update");
    model.addAttribute("pageTitle", "Edit Tag " + tag.getName());
    return "tag-add";
  }

  @RequestMapping(
      value = {"/update"},
      method = {RequestMethod.POST})
  public String updateEditedTag(@Valid Tag tag,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    log.info("tags update called");
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.tag", result);
      redirectAttributes.addFlashAttribute("tag", tag);
      return "redirect:/tags/edit/" + tag.getId();
    }
    Tag initialTag = this.tagService.findById(tag.getId()).get();
    if (!tag.getName().equalsIgnoreCase(initialTag.getName()) && this.tagService
        .tagNameExists(tag.getName())) {
      redirectAttributes.addFlashAttribute("tag", tag);
      redirectAttributes.addFlashAttribute("notification", Notification
          .build("error", tag.getName() + " already exists<br>Please choose another name"));
      return "redirect:/tags/edit/" + tag.getId();
    }
    this.tagService.save(tag);
    Map<String, String> notification = Notification
        .build("success", "Successfully updated tag " + tag.getName());
    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/tags";
  }

  @RequestMapping(
      value = {"/delete/{tagId}"},
      method = {RequestMethod.POST, RequestMethod.GET})
  public String deleteTag(@PathVariable(name = "tagId") Long tagId,
      RedirectAttributes redirectAttributes) {
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

          Map<String, String> notification = Notification
              .build("success", "Successfully deleted tag " + tag.getName());
          redirectAttributes.addFlashAttribute("notification", notification);
        });

    return "redirect:/tags";
  }

  @RequestMapping(
      value = {"/save"},
      method = {RequestMethod.POST})
  public String addNewTag(@Valid Tag tag,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    log.info("SAVE TAG CALLED");
    log.info("SAVING CALLED FOR TAG: {}", tag);
    if (result.hasErrors()) {
      log.info("in has errors");
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.tag", result);
      redirectAttributes.addFlashAttribute("tag", tag);
      return "redirect:/tags/add";
    }
    log.info("TAG EXISTS {}", this.tagService.tagNameExists(tag.getName()));
    if (this.tagService.tagNameExists(tag.getName())) {
      log.info("IN tagNameExists");
      Map<String, String> notficationError = Notification
          .build("error", tag.getName() + " already exists<br>Please choose another name");
      redirectAttributes.addFlashAttribute("notification", notficationError);
      redirectAttributes.addFlashAttribute("tag", tag);
      return "redirect:/tags/add";
    }

    this.tagService.save(tag);
    Map<String, String> notification = Notification
        .build("success", "Successfully added tag " + tag.getName());

    redirectAttributes.addFlashAttribute("notification", notification);
    return "redirect:/tags";
  }

}
