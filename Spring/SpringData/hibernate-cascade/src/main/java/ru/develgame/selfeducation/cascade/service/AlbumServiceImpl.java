package ru.develgame.selfeducation.cascade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;
import ru.develgame.selfeducation.cascade.entity.Album;
import ru.develgame.selfeducation.cascade.entity.Author;
import ru.develgame.selfeducation.cascade.mapper.AlbumMapper;
import ru.develgame.selfeducation.cascade.repository.AlbumRepository;
import ru.develgame.selfeducation.cascade.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    @Transactional
    public ValidatedResponseDto<AlbumDtoResponse> createOne(Long authorId, AlbumDtoRequest albumDtoRequest) {
        if (albumDtoRequest.name() == null || albumDtoRequest.name().isBlank()) {
            return ValidatedResponseDto.<AlbumDtoResponse>builder()
                    .errors(List.of("Name cannot be empty"))
                    .build();
        }

        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return ValidatedResponseDto.<AlbumDtoResponse>builder()
                    .errors(List.of(String.format("Author not found by id %d", authorId)))
                    .build();
        }

        Album album = new Album();
        album.setName(albumDtoRequest.name());
        album.setAuthor(author.get());
        author.get().getAlbums().add(album);
        authorRepository.save(author.get());

        return ValidatedResponseDto.<AlbumDtoResponse>builder()
                .data(albumMapper.toDto(album))
                .build();
    }
}
