package expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "Required")
    @NotEmpty(message = "Required")
    private String name;
    @Column
    private String color;
    @Column(columnDefinition = "clob")
    @Lob
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*@ManyToOne
    @JoinColumn(name = "tag_expense_id")
    private Expense expense;*/
}
