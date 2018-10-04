package expense.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Ovidiu on 03-Oct-18.
 */

@Entity
@Data
public class Category {
    private static final String DEFAULT_COLOR = "#fff";
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String color;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.color = DEFAULT_COLOR;
    }

    public Category(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }


}

