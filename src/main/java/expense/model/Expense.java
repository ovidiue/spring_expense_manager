package expense.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Data
@ToString(exclude = "rates")
@Slf4j
@Transactional
public class Expense {
    @Column
    @NotNull(message = "Required")
    @NotEmpty(message = "Required")
    private String title;
    @Column(columnDefinition = "clob")
    @Lob
    private String description;
    @Column
    private boolean recurrent;
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdOn;
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;
    @Column
    @NotNull(message = "Required: must be a number")
    private Double amount;
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Double payed;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Tag> tags;
    @ManyToOne
    private Category category;
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private List<Rate> rates;

    public Expense() {
        this.createdOn = new Date();
        this.payed = 0.0;
    }

    public void addRate(Rate rate) {
        this.rates.add(rate);
        this.payed += rate.getAmount();
    }

    public void removeRate(Rate rate) {
        this.rates.remove(rate);
        this.payed -= rate.getAmount();
    }
}
