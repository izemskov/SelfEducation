package ru.develgame.selfeducation.cascade.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.entity.Author;

@Component
public class AuthorMapper {
    @Autowired
    private AlbumMapper albumMapper;

    public AuthorDtoResponse toDto(Author author) {
        return AuthorDtoResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .secondName(author.getSecondName())
                .albums(author.getAlbums().stream().map(t -> albumMapper.toDto(t)).toList())
                .build();
    }
}
