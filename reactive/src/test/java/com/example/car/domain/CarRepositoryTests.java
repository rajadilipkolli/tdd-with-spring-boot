package com.example.car.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.car.common.AbstractMongoDbContainer;

import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CarRepositoryTests extends AbstractMongoDbContainer {

	@Autowired
    private CarRepository carRepository;

    @BeforeAll
    void setUp() {
        this.carRepository.save(new Car("prius", "hybrid"))
			.then()
			.block();
    }

    @AfterAll
    void tearDown() {
        carRepository.deleteAll()
			.then()
			.block();
    }

    @Test
    void findByName_returnsCar() {
		StepVerifier.create(carRepository.findByName("prius"))
				.consumeNextWith(car -> {
					assertThat(car.getName()).isEqualTo("prius");
					assertThat(car.getType()).isEqualTo("hybrid");
				})
				.verifyComplete();
    }

}