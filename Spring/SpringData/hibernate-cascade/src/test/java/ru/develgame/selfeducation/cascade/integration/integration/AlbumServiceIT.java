package ru.develgame.selfeducation.cascade.integration.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;
import ru.develgame.selfeducation.cascade.entity.Album;
import ru.develgame.selfeducation.cascade.entity.Author;
import ru.develgame.selfeducation.cascade.integration.config.BasePostgresIT;
import ru.develgame.selfeducation.cascade.repository.AlbumRepository;
import ru.develgame.selfeducation.cascade.repository.AuthorRepository;
import ru.develgame.selfeducation.cascade.service.AlbumService;

import java.util.List;

class AlbumServiceIT extends BasePostgresIT {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumService albumService;

    @BeforeEach
    public void setUp() {
        Author author = new Author();
        author.setFirstName("test");
        author.setSecondName("test1");
        authorRepository.save(author);
    }

    @AfterEach
    public void cleanUp() {
        authorRepository.deleteAll();
    }

    @Test
    @Transactional
    void testAlbumServiceFetchAll() {
        Album album = new Album();
        album.setName("test");

        Album album1 = new Album();
        album.setName("test2");

        Author author = authorRepository.findAll().get(0);
        author.getAlbums().addAll(List.of(album, album1));
        authorRepository.save(author);

        List<AlbumDtoResponse> albumDtoResponses = albumService.fetchAll(author.getId());
        Assertions.assertEquals(2, albumDtoResponses.size());
    }

    @Test
    void testAlbumServiceCreateOne() {
        List<Album> actual = albumRepository.findAll();
        Assertions.assertEquals(0, actual.size());

        Author author = authorRepository.findAll().get(0);
        authorRepository.save(author);

        ValidatedResponseDto<AlbumDtoResponse> test = albumService.createOne(author.getId(), new AlbumDtoRequest("test"));
        Assertions.assertEquals("test", test.data().name());
        actual = albumRepository.findAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("test", actual.get(0).getName());
    }
}
