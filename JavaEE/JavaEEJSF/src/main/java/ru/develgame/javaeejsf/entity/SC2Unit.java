package ru.develgame.javaeejsf.entity;

/**
 * @author Ilya Zemskov
 */
public class SC2Unit {
    private int id;
    private String name;
    private Double attack;
    private Double defense;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SC2Unit(int id, String name, Double attack, Double defense) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }
}
