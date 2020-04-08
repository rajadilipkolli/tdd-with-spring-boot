package com.example.car.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CarRepositoryTests {

	@Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() throws Exception {
        this.carRepository.save(new Car("prius", "hybrid"))
			.then()
			.block();
    }

    @AfterEach
    public void tearDown() throws Exception {
        carRepository.deleteAll()
			.then()
			.block();
    }

    @Test
    public void findByName_returnsCar() {
		StepVerifier.create(carRepository.findByName("prius"))
				.consumeNextWith(car -> {
					assertThat(car.getName()).isEqualTo("prius");
					assertThat(car.getType()).isEqualTo("hybrid");
				})
				.verifyComplete();
    }

}