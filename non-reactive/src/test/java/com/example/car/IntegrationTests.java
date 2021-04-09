package com.example.car;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTests extends DBContainerConfiguration {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CarRepository carRepository;

	@Test
	public void getCar_WithName_ReturnsCar() {
		Car car = new Car();
		car.setName("prius");
		car.setType("hybrid");
		this.carRepository.save(car);
		ResponseEntity<Car> responseEntity = this.testRestTemplate.getForEntity("/cars/{name}", Car.class, "prius");
		car = responseEntity.getBody();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(car).isNotNull();
		assertThat(car.getName()).isEqualTo("prius");
		assertThat(car.getType()).isEqualTo("hybrid");
		this.carRepository.deleteById(car.getId());
	}

	@Test
	public void getCar_WithName_ReturnsNoCar() {
		ResponseEntity<Car> responseEntity = this.testRestTemplate.getForEntity("/cars/{name}", Car.class, "junit");
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		Car car = responseEntity.getBody();
		assertThat(car).isNotNull();
		assertThat(car.getId()).isNull();
		assertThat(car.getName()).isNull();
		assertThat(car.getType()).isNull();
	}

}
