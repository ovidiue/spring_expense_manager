package expense.model;

import javax.persistence.*;

/**
 * Created by Ovidiu on 03-Oct-18.
 */

@Entity

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
    private Long id;

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

