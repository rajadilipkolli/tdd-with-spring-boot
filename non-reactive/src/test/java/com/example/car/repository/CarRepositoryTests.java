package com.example.car.repository;

import com.example.car.domain.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTests {

	@Autowired
	private CarRepository repository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByName_ReturnsCar() {
		Car savedCar = testEntityManager.persistFlushFind(new Car("prius", "hybrid"));
		Optional<Car> optionalCar = this.repository.findByName("prius");
		assertThat(optionalCar).isPresent();
		Car car = optionalCar.get();
		assertThat(car.getId()).isPositive();
		assertThat(car.getName()).isEqualTo(savedCar.getName());
		assertThat(car.getType()).isEqualTo(savedCar.getType());
	}

	@Test
	void testSaveCar() {

		Car requestedCar = new Car("BMW", "hybrid");
		Car persistedCar = this.repository.save(requestedCar);
		assertThat(persistedCar).isNotNull();
		assertThat(persistedCar.getId()).isPositive();
		assertThat(persistedCar.getName()).isEqualTo(requestedCar.getName());
		assertThat(persistedCar.getType()).isEqualTo(requestedCar.getType());
	}
}