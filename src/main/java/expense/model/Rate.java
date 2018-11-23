package expense.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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

    //@JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    private Expense expense;

    public Rate() {
        this.creationDate = new Date();
    }
}
