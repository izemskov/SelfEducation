package ru.develgame.selfeducation.cascade.service;

import ru.develgame.selfeducation.cascade.dto.AlbumDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;

import java.util.List;

public interface AlbumService {
    ValidatedResponseDto<List<AlbumDtoResponse>> fetchAll(Long authorId);

    ValidatedResponseDto<AlbumDtoResponse> createOne(Long authorId, AlbumDtoRequest albumDtoRequest);
}
