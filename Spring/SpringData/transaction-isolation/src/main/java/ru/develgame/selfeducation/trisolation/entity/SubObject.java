package ru.develgame.selfeducation.trisolation.entity;

import jakarta.persistence.*;

@Entity
public class SubObject {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private SomeObject someObject;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SomeObject getSomeObject() {
        return someObject;
    }

    public void setSomeObject(SomeObject someObject) {
        this.someObject = someObject;
    }
}
