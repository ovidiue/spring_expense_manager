package expense.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Data
public class Rate {
    @Column
    private Double amount;
    @Column
    private Date date;
    @Column
    private String observation;
    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;
    @Id
    @GeneratedValue
    private Long id;
}
