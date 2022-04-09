package ru.develgame.javaeejsf.service;

import ru.develgame.javaeejsf.entity.SC2Unit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Zemskov
 */
@Named
@ApplicationScoped
public class SC2UnitService {
    private List<SC2Unit> sc2UnitList = new ArrayList<>();

    public SC2UnitService() {
        SC2Unit zerling = new SC2Unit(0, "Zerling", 1.0d, 1.0d);
        SC2Unit hydralisk = new SC2Unit(1, "Hydralisk", 10.0d, 5.0d);
        sc2UnitList.add(zerling);
        sc2UnitList.add(hydralisk);
    }

    public List<SC2Unit> getSc2UnitList() {
        return sc2UnitList;
    }
}
