package ru.develgame.selfeducation.trisolation.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectDto;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectRequest;
import ru.develgame.selfeducation.trisolation.dto.SubObjectDto;
import ru.develgame.selfeducation.trisolation.entity.SomeObject;
import ru.develgame.selfeducation.trisolation.repository.SomeObjectRepository;

import java.util.stream.Collectors;

public abstract class SomeObjectServiceImpl implements SomeObjectService {
    @Autowired
    private SomeObjectRepository someObjectRepository;

    public SomeObjectDto create(SomeObjectRequest someObjectRequest) {
        SomeObject someObject = new SomeObject();
        someObject.setName(someObjectRequest.name());
        someObject = someObjectRepository.save(someObject);
        return new SomeObjectDto(someObject.getId(), someObject.getName(),
                someObject.getSubObjectList().stream()
                        .map(t -> new SubObjectDto(t.getId(), t.getName()))
                        .collect(Collectors.toList()));
    }

    public SomeObjectDto getSomeObject(Long id) {
        SomeObject someObject = someObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Some object not found"));

        return new SomeObjectDto(someObject.getId(), someObject.getName(),
                someObject.getSubObjectList().stream()
                        .map(t -> new SubObjectDto(t.getId(), t.getName()))
                        .collect(Collectors.toList()));
    }
}
