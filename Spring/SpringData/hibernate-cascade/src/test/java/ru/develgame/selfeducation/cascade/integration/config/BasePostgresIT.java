package ru.develgame.selfeducation.cascade.integration.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = BasePostgresIT.Initializer.class,
        classes = {
                TestCommonConfig.class
        })
public class BasePostgresIT {
    private static final PostgreSQLContainer postgresSqlContainer;

    static {
        postgresSqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres")
                .withTag("12"))
                .withDatabaseName("test4");
        postgresSqlContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            HashMap<String, Object> properties = new HashMap<>();
            properties.put("spring.datasource.username", postgresSqlContainer.getUsername());
            properties.put("spring.datasource.password", postgresSqlContainer.getPassword());
            properties.put("spring.datasource.url", postgresSqlContainer.getJdbcUrl());
            MapPropertySource propertySource = new MapPropertySource("postgresql-container", properties);
            ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
            environment.getPropertySources().addFirst(propertySource);
        }
    }
}
