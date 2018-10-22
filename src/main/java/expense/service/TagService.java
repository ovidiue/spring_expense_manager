package expense.service;

import expense.model.Tag;
import expense.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> findAllByIds(List<Long> ids) {
        return tagRepository.findAllByIds(ids);
    }
}
