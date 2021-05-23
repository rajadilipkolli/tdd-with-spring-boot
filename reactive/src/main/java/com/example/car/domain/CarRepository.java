package com.example.car.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CarRepository extends ReactiveMongoRepository<Car, String> {

	Mono<Car> findByName(String name);

}
