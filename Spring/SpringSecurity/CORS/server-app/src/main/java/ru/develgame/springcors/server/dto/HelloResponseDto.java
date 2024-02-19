package ru.develgame.springcors.server.dto;

import lombok.Builder;

@Builder
public record HelloResponseDto(String greeting) {
}
