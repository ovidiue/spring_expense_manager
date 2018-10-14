package expense.web.controller;

import expense.model.Tag;
import expense.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Ovidiu on 13-Oct-18.
 */
@Controller
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String getTags(Model model) {
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        return "tags-listing";
    }

    @RequestMapping(value = {"/tags/add"})
    public String addTag(Tag tag) {
        tagService.save(tag);
        return "tag-add";
    }

}
