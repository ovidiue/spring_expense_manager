package expense.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
public class Rate {
    @Column
    private Double amount;
    @Column
    private Date date;
    @Column
    private String observation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expense_id")
    private Expense expense;
    @Id
    @GeneratedValue
    private Long id;

    public Rate(Double amount, Date date, String observation) {
        this.amount = amount;
        this.date = date;
        this.observation = observation;
    }

}
