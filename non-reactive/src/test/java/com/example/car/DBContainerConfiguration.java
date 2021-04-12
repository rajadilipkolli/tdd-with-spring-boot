package com.example.car;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DBContainerConfiguration {

    @Container
    protected static final PostgreSQLContainer<?> sqlContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("integration-tests-db")
                    .withUsername("username")
                    .withPassword("password");

    @DynamicPropertySource
    static void setSqlContainer(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", sqlContainer::getUsername);
        propertyRegistry.add("spring.datasource.password", sqlContainer::getPassword);
    }
}
