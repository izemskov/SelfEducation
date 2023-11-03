package ru.develgame.selfeducation.cascade.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthorDtoResponse(Long id,
                                String firstName,
                                String secondName,
                                List<AlbumDtoResponse> albums) {
}
