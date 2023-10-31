package ru.develgame.selfeducation.cascade.dto;

import lombok.Builder;

@Builder
public record AuthorDtoResponse(Long id, String firstName, String secondName) {
}
