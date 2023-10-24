package ru.develgame.selfeducation.trisolation.dto;

import java.util.List;

public record SomeObjectDto(Long id, String name, List<SubObjectDto> subObjectDtoList) {
}
