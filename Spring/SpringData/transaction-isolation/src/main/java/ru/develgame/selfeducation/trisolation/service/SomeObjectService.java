package ru.develgame.selfeducation.trisolation.service;

import ru.develgame.selfeducation.trisolation.dto.SomeObjectDto;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectRequest;

public interface SomeObjectService {
    SomeObjectDto create(SomeObjectRequest someObjectRequest);

    SomeObjectDto edit(Long id, SomeObjectRequest someObjectRequest);

    SomeObjectDto addAttachment(Long id);

    SomeObjectDto getSomeObject(Long id);
}
