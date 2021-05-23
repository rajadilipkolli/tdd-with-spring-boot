package com.example.car;

import com.example.car.domain.Car;
import com.example.car.repository.CarRepository;
import com.example.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
class CachingTest {

	@Autowired
	private CarService carService;

	@MockBean
	private CarRepository carRepository;

	@Test
	void getCar_ReturnsCachedValue() {
		Car car = new Car("prius", "hybrid");
		given(carRepository.findByName("prius")).willReturn(Optional.of(car));
		carService.getCarDetails("prius");
		carService.getCarDetails("prius");
		verify(carRepository, times(1)).findByName("prius");
	}
}
