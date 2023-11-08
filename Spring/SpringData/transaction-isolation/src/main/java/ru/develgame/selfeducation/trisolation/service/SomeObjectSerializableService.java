package ru.develgame.selfeducation.trisolation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectDto;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectRequest;
import ru.develgame.selfeducation.trisolation.dto.SubObjectDto;
import ru.develgame.selfeducation.trisolation.entity.SomeObject;
import ru.develgame.selfeducation.trisolation.entity.SubObject;
import ru.develgame.selfeducation.trisolation.repository.SomeObjectRepository;
import ru.develgame.selfeducation.trisolation.repository.SubObjectRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SomeObjectSerializableService implements SomeObjectService {
    @Autowired
    private SomeObjectRepository someObjectRepository;

    @Autowired
    private SubObjectRepository subObjectRepository;

    public SomeObjectDto create(SomeObjectRequest someObjectRequest) {
        SomeObject someObject = new SomeObject();
        someObject.setName(someObjectRequest.name());
        someObject = someObjectRepository.save(someObject);
        return new SomeObjectDto(someObject.getId(), someObject.getName(),
                someObject.getSubObjectList().stream()
                        .map(t -> new SubObjectDto(t.getId(), t.getName()))
                        .collect(Collectors.toList()));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable
    public SomeObjectDto edit(Long id, SomeObjectRequest someObjectRequest) {
        SomeObject someObject = someObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Some object not found"));
        someObject.setName(someObject.getName() + someObjectRequest.name());
        someObject = someObjectRepository.save(someObject);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return new SomeObjectDto(someObject.getId(), someObject.getName(),
                someObject.getSubObjectList().stream()
                        .map(t -> new SubObjectDto(t.getId(), t.getName()))
                        .collect(Collectors.toList()));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SomeObjectDto addAttachment(Long id) {
        SomeObject someObject = someObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Some object not found"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        SubObject subObject = new SubObject();
        subObject.setName(UUID.randomUUID().toString());
        subObject = subObjectRepository.save(subObject);

        someObject.getSubObjectList().add(subObject);
        subObject.setSomeObject(someObject);
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
