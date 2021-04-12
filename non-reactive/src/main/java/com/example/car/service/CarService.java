package com.example.car.service;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import com.example.car.web.CarNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

	private CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Cacheable("cars")
	public Car getCarDetails(String name) {
		Car car = carRepository.findByName(name);
		if(car == null) {
			throw new CarNotFoundException();
		}
		return car;
	}

	public Car saveCar(Car requestedCar) {
		return this.carRepository.save(requestedCar);
	}
}
