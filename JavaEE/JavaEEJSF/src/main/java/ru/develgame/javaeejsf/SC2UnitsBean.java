package ru.develgame.javaeejsf;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.file.UploadedFile;
import ru.develgame.javaeecommon.entity.SC2Unit;
import ru.develgame.javaeejsf.datamodels.LazySC2UnitDataModel;
import ru.develgame.javaeejsf.service.SC2UnitService;
import ru.develgame.javaeesoap.client.SC2WebService;
import ru.develgame.javaeesoap.client.SC2WebServiceService;
import ru.develgame.javaeesoap.client.Sc2Unit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ilya Zemskov
 */
@Named("sc2UnitsBean")
@SessionScoped
public class SC2UnitsBean implements Serializable {
    @Inject
    private SC2UnitService sc2UnitService;

    private LazyDataModel<SC2Unit> lazyModel;

    private UploadedFile file;

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

    private List<SC2Unit> selectedSC2Unit = new ArrayList<>();

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

        if (file != null) {
            file.getContent();
        }

        Response res = sc2UnitService.createSc2Unit(new SC2Unit(1L, name, attackD, defenseD));
        if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(name + " created successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage("Error: " + res.getEntity()));
        }

        name = null;
        attack = null;
        defense = null;
    }

    public void compare() {
        if (selectedSC2Unit.size() < 2) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Select two units"));
            return;
        }

        List<Sc2Unit> soapSC2Units = selectedSC2Unit.stream().limit(2).map(t -> {
            Sc2Unit sc2Unit = new Sc2Unit();
            sc2Unit.setAttack(t.getAttack());
            sc2Unit.setDefense(t.getDefense());
            sc2Unit.setId(t.getId());
            sc2Unit.setName(t.getName());
            return sc2Unit;
        }).collect(Collectors.toList());

        SC2WebServiceService sc2WebServiceService = new SC2WebServiceService();
        SC2WebService sc2WebServiceProxy = sc2WebServiceService.getSC2WebServicePort();
        int res = sc2WebServiceProxy.fight(soapSC2Units.get(0), soapSC2Units.get(1));
        if (res > 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(soapSC2Units.get(0).getName() + " win"));
        }
        else if (res < 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(soapSC2Units.get(1).getName() + " win"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Units equals"));
        }
    }

    public LazyDataModel<SC2Unit> getLazyModel() {
        return lazyModel;
    }

    public List<SC2Unit> getSelectedSC2Unit() {
        return selectedSC2Unit;
    }

    public void setSelectedSC2Unit(List<SC2Unit> selectedSC2Unit) {
        this.selectedSC2Unit = selectedSC2Unit;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
