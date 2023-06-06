package com.example.car;

import com.example.car.domain.Car;
import com.example.car.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class IntegrationTests {

	@Container
	@ServiceConnection
	static final PostgreSQLContainer<?> sqlContainer =
			new PostgreSQLContainer<>("postgres:latest");

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CarRepository carRepository;

	@Test
	void getCar_WithName_ReturnsCar() {
		Car car = new Car("prius", "hybrid");
		this.carRepository.save(car);
		ResponseEntity<Car> responseEntity = this.testRestTemplate.getForEntity("/cars/{name}", Car.class, "prius");
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		car = responseEntity.getBody();
		assertThat(car.getName()).isEqualTo("prius");
		assertThat(car.getType()).isEqualTo("hybrid");
	}

	@Test
	void getCar_WithName_ReturnsNoCar() {
		ResponseEntity<Car> responseEntity = this.testRestTemplate.getForEntity("/cars/{name}", Car.class, "junit");
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		Car car = responseEntity.getBody();
		assertThat(car).isNotNull();
		assertThat(car.getId()).isNull();
		assertThat(car.getName()).isNull();
		assertThat(car.getType()).isNull();
	}

}
