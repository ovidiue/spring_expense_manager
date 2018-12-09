package expense.repository;

/**
 * Created by Ovidiu on 09-Dec-18.
 */
public interface ExpenseBasic {

  Long getId();

  String getTitle();

  Double getAmount();

  Double getPayed();
}
