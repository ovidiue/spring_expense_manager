package expense.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Date creationDate;
    @Column
    private String observation;
    @Column
    private Date payedOn;
    @Id
    @GeneratedValue
    private Long id;
}
