package ru.develgame.javaeejsf.datamodels;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import ru.develgame.javaeejsf.entity.SC2Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ilya Zemskov
 */
public class LazySC2UnitDataModel extends LazyDataModel<SC2Unit> {
    private List<SC2Unit> data;

    public LazySC2UnitDataModel(List<SC2Unit> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        return data.size();
    }

    @Override
    public List<SC2Unit> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return new ArrayList<>(data);
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
