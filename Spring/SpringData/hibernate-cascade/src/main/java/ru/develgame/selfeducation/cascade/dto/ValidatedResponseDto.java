package ru.develgame.selfeducation.cascade.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ValidatedResponseDto<T>(T data,
                                      List<String> errors) {
}
