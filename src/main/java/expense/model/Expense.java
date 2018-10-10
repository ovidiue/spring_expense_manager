package expense.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Data
public class Expense {
    @Column
    private String title;
    @Column(columnDefinition = "clob")
    @Lob
    private String description;
    @Column
    private boolean recurrent;
    @Column
    private Date createdOn;
    @Column
    private Date dueDate;
    @Column
    private Double amount;
    /*@Column*/
    @OneToOne(fetch = FetchType.EAGER)
    private Category category;
    /*@Column*/
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<Tag> tags;
    @OneToMany(mappedBy = "expense",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Rate> payedRates;
    @Id
    @GeneratedValue
    private Long id;
}
