package ru.develgame.selfeducation.cascade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;
import ru.develgame.selfeducation.cascade.entity.Album;
import ru.develgame.selfeducation.cascade.entity.Author;
import ru.develgame.selfeducation.cascade.exception.AlbumForbiddenException;
import ru.develgame.selfeducation.cascade.exception.AlbumNotFoundException;
import ru.develgame.selfeducation.cascade.exception.AuthorNotFoundException;
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
    public List<AlbumDtoResponse> fetchAll(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id %d not found", authorId)))
                .getAlbums().stream().map(t -> albumMapper.toDto(t)).toList();
    }

    @Override
    public AlbumDtoResponse fetchOne(Long authorId, Long albumId) {
        authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id %d not found", authorId)));

        Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(String.format("Album with id %d not found", albumId)));

        if (album.getAuthor().getId() != authorId) {
            throw new AlbumForbiddenException(String.format("Album with id %d don't link with author with id %d", albumId, authorId));
        }

        return albumMapper.toDto(album);
    }

    @Override
    @Transactional
    public ValidatedResponseDto<AlbumDtoResponse> createOne(Long authorId, AlbumDtoRequest albumDtoRequest) {
        if (albumDtoRequest.name() == null || albumDtoRequest.name().isBlank()) {
            return ValidatedResponseDto.<AlbumDtoResponse>builder()
                    .errors(List.of("Name cannot be empty"))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return ValidatedResponseDto.<AlbumDtoResponse>builder()
                    .errors(List.of(String.format("Author not found by id %d", authorId)))
                    .status(HttpStatus.NOT_FOUND.value())
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
