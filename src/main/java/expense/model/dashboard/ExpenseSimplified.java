package expense.model.dashboard;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Ovidiu on 30-Dec-18.
 */
@Data
@AllArgsConstructor
public class ExpenseSimplified {

  private String title;

  private boolean recurrent;

  private Date createdOn;

  private Date dueDate;

  private Double amount;

  private Long id;

  private Double payed;

  private int month;

}
