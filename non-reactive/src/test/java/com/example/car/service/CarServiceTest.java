package com.example.car.service;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import com.example.car.web.CarNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

	@Mock
	private CarRepository carRepository;

	private CarService carService;

	@BeforeEach
	public void setUp() throws Exception {
		carService = new CarService(carRepository);
	}

	@Test
	public void getCarDetails_returnsCarInfo() {
		given(carRepository.findByName("prius")).willReturn(new Car("prius", "hybrid"));

		Car car = carService.getCarDetails("prius");

		assertThat(car.getName()).isEqualTo("prius");
		assertThat(car.getType()).isEqualTo("hybrid");
	}

	@Test
	public void getCarDetails_whenCarNotFound() {
		given(carRepository.findByName("prius")).willReturn(null);

		assertThatThrownBy(() -> carService.getCarDetails("prius")).isInstanceOf(CarNotFoundException.class);
	}
}