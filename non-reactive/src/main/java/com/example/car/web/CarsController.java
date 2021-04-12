package com.example.car.web;

import com.example.car.domain.Car;
import com.example.car.service.CarService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class CarsController {

	private final CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/cars/{name}")
	public Car getCar(@PathVariable String name) {
		Car car = this.carService.getCarDetails(name);
		if (car == null) {
			throw new CarNotFoundException();
		}
		return car;
	}

	@PostMapping("/cars/")
	public ResponseEntity<Object> getCar(@RequestBody Car requestedCar) throws URISyntaxException {
		Car persistedCar = this.carService.saveCar(requestedCar);
		return ResponseEntity.created(new URI("http://localhost:8080/cars/" +persistedCar.getId()))
				.build();
	}

}
