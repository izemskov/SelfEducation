package ru.develgame.javaeerestapi.service;

import ru.develgame.javaeerestapi.entity.SC2Unit;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Zemskov
 */
@Named
public class SC2UnitService {
    private List<SC2Unit> sc2UnitList = new ArrayList<>();

    @PostConstruct
    public void init() {
        SC2Unit zerling = new SC2Unit(1L, "Zerling", 1.0d, 1.0d);
        sc2UnitList.add(zerling);
    }

    public List<SC2Unit> getSC2Units() {
        return new ArrayList<>(sc2UnitList);
    }
}
