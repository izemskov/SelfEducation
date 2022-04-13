package ru.develgame.javaeejsf.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import ru.develgame.javaeecommon.entity.SC2Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ilya Zemskov
 */
public abstract class LazySC2UnitDataModel extends LazyDataModel<SC2Unit> {
    protected List<SC2Unit> data = new ArrayList<>();

    public LazySC2UnitDataModel(List<SC2Unit> data) {
        this.data.addAll(data);
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        return data.size();
    }

    @Override
    public SC2Unit getRowData(String rowKey) {
        for (SC2Unit sc2Unit : data) {
            if (sc2Unit.getId() == Integer.parseInt(rowKey)) {
                return sc2Unit;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(SC2Unit sc2Unit) {
        return String.valueOf(sc2Unit.getId());
    }
}
