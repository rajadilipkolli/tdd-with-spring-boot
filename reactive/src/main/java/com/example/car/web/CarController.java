package com.example.car.web;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import com.example.car.web.exception.DomainException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/cars/{name}")
    public Mono<Car> getCar (@PathVariable String name) {
        return this.carRepository.findByName(name)
                .switchIfEmpty(Mono.error(() ->
                        new DomainException(HttpStatus.NOT_FOUND, "Car with name " + name + " not found")));
    }
}