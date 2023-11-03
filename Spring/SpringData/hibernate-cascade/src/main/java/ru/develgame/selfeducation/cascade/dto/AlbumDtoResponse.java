package ru.develgame.selfeducation.cascade.dto;

import lombok.Builder;

@Builder
public record AlbumDtoResponse(Long id, String name) {
}
