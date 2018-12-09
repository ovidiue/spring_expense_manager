package expense.service;

import expense.model.Tag;
import expense.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ovidiu on 10-Oct-18.
 */
@Service
public class TagService {

  @Autowired
  private TagRepository tagRepository;

  public void deleteTag(Tag tag) {
    tagRepository.delete(tag);
  }

  public List<Tag> findAll() {
    return tagRepository.findAll();
  }

  public Tag save(Tag tag) {
    return tagRepository.save(tag);
  }

  public Optional<List<Tag>> findAllByIds(List<Long> ids) {
    return Optional.ofNullable(this.tagRepository.findAllByIds(ids));
  }

  public Optional<Tag> findById(Long id) {
    return tagRepository.findById(id);
  }
}
