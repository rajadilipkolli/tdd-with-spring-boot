package com.example.car.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class MongoDbTestContainerConfiguration {
    
    private static final DockerImageName MONGO_DOCKER_IMAGE = DockerImageName.parse("mongo:6.0.5");

	@Bean
	@ServiceConnection
	MongoDBContainer mongoDBContainer() {
		return new MongoDBContainer(MONGO_DOCKER_IMAGE);
	}

}
