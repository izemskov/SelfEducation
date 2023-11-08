package ru.develgame.selfeducation.cascade.dto;

import lombok.Builder;

@Builder
public record PhotoDtoResponse(Long id, String title, String filename) {
}
