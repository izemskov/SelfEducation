package ru.develgame.javaeejsf.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Ilya Zemskov
 */
@XmlRootElement
public class SC2Unit {
    private Long id;
    private String name;
    private Double attack;
    private Double defense;

    public SC2Unit() {
    }

    public SC2Unit(Long id, String name, Double attack, Double defense) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAttack() {
        return attack;
    }

    public void setAttack(Double attack) {
        this.attack = attack;
    }

    public Double getDefense() {
        return defense;
    }

    public void setDefense(Double defense) {
        this.defense = defense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SC2Unit sc2Unit = (SC2Unit) o;
        return Objects.equals(id, sc2Unit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
