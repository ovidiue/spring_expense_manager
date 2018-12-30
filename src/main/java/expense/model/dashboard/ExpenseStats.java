package expense.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Ovidiu on 30-Dec-18.
 */
@Data
@AllArgsConstructor
public class ExpenseStats {

  private long noOfExpenses;
  private double max;
  private double min;
  private double averageNonRecurrent;
  private double averageRecurrent;
  private double partialPayed;
  private double payed;
}
