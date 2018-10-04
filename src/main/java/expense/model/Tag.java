package expense.model;

import javax.persistence.*;

/**
 * Created by Ovidiu on 03-Oct-18.
 */
@Entity
@Table(name = "tags")
public class Tag {
    @Column
    private String name;
    @Column
    private String color;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
