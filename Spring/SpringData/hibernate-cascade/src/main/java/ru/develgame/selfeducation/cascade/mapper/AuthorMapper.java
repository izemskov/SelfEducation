package ru.develgame.selfeducation.cascade.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.entity.Author;

@Component
public class AuthorMapper {
    public AuthorDtoResponse toDto(Author author) {
        return AuthorDtoResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .secondName(author.getSecondName())
                .build();
    }
}
