package expense.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Data
@ToString(exclude = "expense")
public class Rate {
    @Column
    @NotNull(message = "Required: must be a number")
    private Double amount;
    @Column
    private Date creationDate;
    @Column(columnDefinition = "clob")
    @Lob
    private String observation;
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date payedOn;
    @Id
    @GeneratedValue
    private Long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JsonManagedReference
    private Expense expense;

    public Rate() {
        this.creationDate = new Date();
    }
}
