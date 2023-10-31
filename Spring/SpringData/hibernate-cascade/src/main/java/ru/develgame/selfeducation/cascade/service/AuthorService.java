package ru.develgame.selfeducation.cascade.service;

import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;

import java.util.List;

public interface AuthorService {
    List<AuthorDtoResponse> fetchAll();
}
