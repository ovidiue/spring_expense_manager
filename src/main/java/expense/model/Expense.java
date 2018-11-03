package expense.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Data
@Slf4j
@Transactional
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
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;
    @ManyToOne
    private Category category;
/*    @OneToMany
    private List<Rate> rates;*/
}
