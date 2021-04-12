package com.example.controller;

import com.example.domain.Car;
import com.example.service.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{name}")
    public Car getCar(@PathVariable String name) {
        return this.carService.getCarDetails(name);
    }
}
