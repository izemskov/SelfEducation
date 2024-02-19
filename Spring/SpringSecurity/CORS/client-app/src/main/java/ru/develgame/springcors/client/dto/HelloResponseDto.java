package ru.develgame.springcors.client.dto;

import lombok.Builder;

@Builder
public record HelloResponseDto(String greeting) {
}
