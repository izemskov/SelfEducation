package ru.develgame.javaeejsf;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import ru.develgame.javaeecommon.entity.SC2Unit;
import ru.develgame.javaeejsf.datamodels.LazySC2UnitDataModel;
import ru.develgame.javaeejsf.service.SC2UnitService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ilya Zemskov
 */
@Named("sc2UnitsBean")
@SessionScoped
public class SC2UnitsBean implements Serializable {
    @Inject
    private SC2UnitService sc2UnitService;

    private LazyDataModel<SC2Unit> lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = new LazySC2UnitDataModel(sc2UnitService.getSc2UnitList()) {
            @Override
            public List<SC2Unit> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                data.clear();
                data.addAll(sc2UnitService.getSc2UnitList());
                return data;
            }
        };
    }

    private SC2Unit selectedSC2Unit;

    private String name;
    private String attack;
    private String defense;

    public void save() {
        if (name == null || name.isEmpty())
            return;
        if (attack == null || attack.isEmpty())
            return;
        if (defense == null || defense.isEmpty())
            return;

        double attackD = Double.parseDouble(attack);
        double defenseD = Double.parseDouble(defense);

        Response res = sc2UnitService.createSc2Unit(new SC2Unit(1L, name, attackD, defenseD));
        if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(name + " created successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error: " + res.getEntity()));
        }
    }

    public LazyDataModel<SC2Unit> getLazyModel() {
        return lazyModel;
    }

    public SC2Unit getSelectedSC2Unit() {
        return selectedSC2Unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }
}
