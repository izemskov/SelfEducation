package ru.develgame.selfeducation.cascade.integration.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.entity.Author;
import ru.develgame.selfeducation.cascade.integration.config.BasePostgresIT;
import ru.develgame.selfeducation.cascade.repository.AuthorRepository;
import ru.develgame.selfeducation.cascade.service.AuthorService;

import java.util.List;

class AuthorServiceIT extends BasePostgresIT {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @AfterEach
    public void cleanUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testAuthorServiceFetchAll() {
        Author author = new Author();
        author.setFirstName("test");
        authorRepository.save(author);

        Author author2 = new Author();
        author2.setFirstName("test2");
        authorRepository.save(author2);

        List<AuthorDtoResponse> authorDtoResponses = authorService.fetchAll();
        Assertions.assertEquals(2, authorDtoResponses.size());
        Assertions.assertTrue(authorDtoResponses.stream().anyMatch(t -> t.firstName().equals("test")));
        Assertions.assertTrue(authorDtoResponses.stream().anyMatch(t -> t.firstName().equals("test2")));
    }

    @Test
    void testAuthorServiceCreateAuthor() {
        Assertions.assertEquals(0, authorRepository.findAll().size());
        authorService.createOne(new AuthorDtoRequest("test", "test1"));
        Assertions.assertEquals(1, authorRepository.findAll().size());
        Assertions.assertEquals("test", authorRepository.findAll().get(0).getFirstName());
        Assertions.assertEquals("test1", authorRepository.findAll().get(0).getSecondName());
    }

    @Test
    void testAuthorServiceDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("test");
        author = authorRepository.save(author);

        Assertions.assertEquals(1, authorRepository.findAll().size());

        authorService.deleteOne(author.getId());

        Assertions.assertEquals(0, authorRepository.findAll().size());
    }
}
