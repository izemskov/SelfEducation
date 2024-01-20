package ru.develgame.selfeducation.cascade.integration.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.develgame.selfeducation.cascade.CascadeApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CascadeApp.class)
@ContextConfiguration(initializers = {BasePostgresIT.Initializer.class})
public class BasePostgresIT {
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
}
