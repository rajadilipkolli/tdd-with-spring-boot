package com.example.car;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")
class IntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ReactiveMongoOperations operations;

	static DockerImageName mongoDockerImageName = DockerImageName.parse("mongo:5.0");

	@Container
	protected static final MongoDBContainer MONGO_DB_CONTAINER =
			new MongoDBContainer(mongoDockerImageName).withExposedPorts(27017);

	static {
		MONGO_DB_CONTAINER.start();
	}

	@DynamicPropertySource
	static void setMongoDbContainerURI(DynamicPropertyRegistry propertyRegistry) {
		propertyRegistry.add("spring.data.mongodb.host", MONGO_DB_CONTAINER::getHost);
		propertyRegistry.add("spring.data.mongodb.port", MONGO_DB_CONTAINER::getFirstMappedPort);
	}

	@BeforeAll
	public void setUp() {
		this.operations
				.createCollection(Car.class, CollectionOptions.empty().size(1024 * 1024).maxDocuments( 100).capped())
				.then()
				.block();

		this.carRepository
				.save(new Car("prius", "hybrid"))
				.then()
				.block();
	}

	@AfterAll
	public void tearDown() {
		this.operations.dropCollection(Car.class).block();
	}

	@Test
	void test() {
		assertThat(MONGO_DB_CONTAINER.isRunning()).isTrue();
	}

	@Test
	void getCar_WithName_ReturnsCar() {
		Car car = this.webTestClient.get().uri("/cars/{name}", "prius")
				.exchange().expectStatus().isOk()
				.expectBody(Car.class).returnResult().getResponseBody();
		assertThat(car).isNotNull();
		assertThat(car.getId()).isNotNull();
		assertThat(car.getName()).isEqualTo("prius");
		assertThat(car.getType()).isEqualTo("hybrid");
	}

	@Test
	void getCar_WithName_ReturnsNoCar() {
		this.webTestClient.get().uri("/cars/{name}", "junit")
				.exchange().expectStatus().isNotFound()
				.expectBody()
					.jsonPath("$.message", "Car with name junit not found");
	}

}
