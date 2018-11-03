package expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Tag {
    @Column
    private String name;
    @Column
    private String color;
    @Column
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*@ManyToOne
    @JoinColumn(name = "tag_expense_id")
    private Expense expense;*/
}
