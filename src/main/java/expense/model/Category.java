package expense.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Ovidiu on 03-Oct-18.
 */

@Entity
@Data
public class Category {
    private static final String DEFAULT_COLOR = "#fff";
    @Column
    @NotNull(message = "Required")
    @NotEmpty(message = "Required")
    private String name;
    @Column(columnDefinition = "clob")
    @Lob
    private String description;
    @Column
    private String color;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

