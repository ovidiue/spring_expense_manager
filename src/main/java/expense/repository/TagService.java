package expense.repository;

import expense.model.Tag;

import java.util.List;

/**
 * Created by Ovidiu on 08-Oct-18.
 */

public interface TagService {
    List<Tag> findAll();

    void save(Tag tag);
}
