package ru.develgame.selfeducation.cascade.service;

import ru.develgame.selfeducation.cascade.dto.AlbumDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;

import java.util.List;

public interface AlbumService {
    List<AlbumDtoResponse> fetchAll(Long authorId);

    AlbumDtoResponse fetchOne(Long authorId, Long albumId);

    ValidatedResponseDto<AlbumDtoResponse> createOne(Long authorId, AlbumDtoRequest albumDtoRequest);
}
