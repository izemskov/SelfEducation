package ru.develgame.selfeducation.cascade.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.entity.Album;

@Component
public class AlbumMapper {
    public AlbumDtoResponse toDto(Album album) {
        return AlbumDtoResponse.builder()
                .name(album.getName())
                .build();
    }
}
