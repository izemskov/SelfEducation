package ru.develgame.javaeejsf;

import org.primefaces.model.LazyDataModel;
import ru.develgame.javaeejsf.datamodels.LazySC2UnitDataModel;
import ru.develgame.javaeejsf.entity.SC2Unit;
import ru.develgame.javaeejsf.service.SC2UnitService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Ilya Zemskov
 */
@Named
@SessionScoped
public class ExampleDataTableBean implements Serializable {
    private LazyDataModel<SC2Unit> lazyModel;

    private SC2Unit selectedSC2Unit;

    @Inject
    private SC2UnitService sc2UnitService;

    @PostConstruct
    public void init() {
        lazyModel = new LazySC2UnitDataModel(sc2UnitService.getSc2UnitList());
    }

    public LazyDataModel<SC2Unit> getLazyModel() {
        return lazyModel;
    }

    public SC2Unit getSelectedSC2Unit() {
        return selectedSC2Unit;
    }
}
