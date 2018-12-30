package expense.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Ovidiu on 28-Dec-18.
 */
@AllArgsConstructor
@Data
public class CategoryStats {

  private String name;
  private String color;
  private double payed;
  private double total;
  private double min;
  private double max;
  private long totalRecurrent;
  private long nonRecurrent;
  private long closed;
  private long noOfExpenses;
}
