package expense.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Data
public class Rate {
    @Column
    @NotNull(message = "Required: must be a number")
    private Double amount;
    @Column
    private Date creationDate;
    @Column(columnDefinition = "clob")
    private String observation;
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date payedOn;
    @Id
    @GeneratedValue
    private Long id;

    public Rate() {
        this.creationDate = new Date();
    }
}
