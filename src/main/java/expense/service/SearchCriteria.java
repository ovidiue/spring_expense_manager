package expense.service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Ovidiu on 28-Nov-18.
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
