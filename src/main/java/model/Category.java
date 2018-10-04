package model;

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

