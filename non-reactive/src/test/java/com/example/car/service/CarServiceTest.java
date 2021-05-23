package com.example.car.service;

import com.example.car.domain.Car;
import com.example.car.repository.CarRepository;
import com.example.car.web.CarNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarService carService;

	@Test
	void getCarDetails_returnsCarInfo() {
		given(carRepository.findByName("prius")).willReturn(Optional.of(new Car("prius", "hybrid")));

		Car car = carService.getCarDetails("prius");

		assertThat(car.getName()).isEqualTo("prius");
		assertThat(car.getType()).isEqualTo("hybrid");
	}

	@Test
	void getCarDetails_whenCarNotFound() {
		given(carRepository.findByName("prius")).willReturn(Optional.empty());

		assertThatThrownBy(() -> carService.getCarDetails("prius")).isInstanceOf(CarNotFoundException.class);
	}

	@Test
	void testSaveCar() {
		Car requestedCar = new Car("BMW", "hybrid");
		given(carRepository.save(any(Car.class))).willReturn(requestedCar);
		Car persistedCar = this.carService.saveCar(requestedCar);
		assertThat(persistedCar.getName()).isEqualTo(requestedCar.getName());
		assertThat(persistedCar.getType()).isEqualTo(requestedCar.getType());
	}

}