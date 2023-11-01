package ru.develgame.selfeducation.cascade.service;

import ru.develgame.selfeducation.cascade.dto.AuthorDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDtoResponse> fetchAll();

    ValidatedResponseDto<AuthorDtoResponse> fetchOne(Long id);

    ValidatedResponseDto<AuthorDtoResponse> createOne(AuthorDtoRequest authorDtoRequest);
}
