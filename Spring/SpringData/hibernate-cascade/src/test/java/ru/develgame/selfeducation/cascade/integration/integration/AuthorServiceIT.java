package ru.develgame.selfeducation.cascade.integration.integration;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.develgame.selfeducation.cascade.CascadeApp;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.entity.Author;
import ru.develgame.selfeducation.cascade.integration.config.BasePostgresIT;
import ru.develgame.selfeducation.cascade.repository.AuthorRepository;
import ru.develgame.selfeducation.cascade.service.AuthorService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CascadeApp.class)
@ContextConfiguration(initializers = {AuthorServiceIT.Initializer.class})
class AuthorServiceIT extends BasePostgresIT {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;


    private static final PostgreSQLContainer postgresSqlContainer;

    static {
        postgresSqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres")
                .withTag("12"))
                .withDatabaseName("test4");
        postgresSqlContainer.start();
    }


    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresSqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresSqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresSqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @AfterEach
    public void cleanUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testFetchAll() {
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
    void testFetchAll2() {
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
}
