package expense.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity

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
    private int id;

    public Expense(String title,
                   String description,
                   boolean recurrent,
                   Date dueDate,
                   Double amount,
                   Category category) {
        this.title = title;
        this.description = description;
        this.recurrent = recurrent;
        this.createdOn = new Date();
        this.dueDate = dueDate;
        this.amount = amount;
        this.category = category;
        this.tags = new ArrayList<>();
        this.payedRates = new ArrayList<>();
    }

    public Expense(String title,
                   String description,
                   boolean recurrent,
                   Date dueDate,
                   Double amount) {
        this.title = title;
        this.description = description;
        this.recurrent = recurrent;
        this.createdOn = new Date();
        this.dueDate = dueDate;
        this.amount = amount;
        this.tags = new ArrayList<>();
        this.payedRates = new ArrayList<>();
    }

    public Expense() {
        this.createdOn = new Date();
    }
}
