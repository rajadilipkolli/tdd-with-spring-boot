package com.example.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestCarsApplication {
    
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> sqlContainer() {
		return new PostgreSQLContainer<>("postgres:latest");
    }

    public static void main(String[] args) {
        SpringApplication.from(CarsApplication::main).with(TestCarsApplication.class).run(args);
    }
}
