package dev.tudos.quizbuilder.core.service;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface AbstractContainerBaseTest {
    String DOCKER_IMAGE_POSTGRES = "postgres:16.3-alpine";

    @Container
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_POSTGRES)
            .withDatabaseName("qb_core_integration_tests")
            .withUsername("usr")
            .withPassword("password");
}
