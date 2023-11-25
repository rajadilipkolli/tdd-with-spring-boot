package com.example.car.service;

import com.example.car.domain.Car;
import com.example.car.repository.CarRepository;
import com.example.car.web.CarNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

	private final CarRepository carRepository;

	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Cacheable(value = "cars", unless = "#result == null")
	public Car getCarDetails(String name) {
		return carRepository.findByName(name).orElseThrow(CarNotFoundException::new);
	}

	public Car saveCar(Car requestedCar) {
		return this.carRepository.save(requestedCar);
	}
}
