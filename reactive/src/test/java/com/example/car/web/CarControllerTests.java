package com.example.car.web;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import com.example.car.web.exception.DomainExceptionWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@WebFluxTest(controllers = CarController.class)
public class CarControllerTests {

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private DomainExceptionWrapper domainExceptionWrapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getCar_WithName_returnsCar() throws Exception {
    	Car car = new Car("prius", "hybrid");
		given(carRepository.findByName("prius")).willReturn(Mono.just(car));

		this.webTestClient.get().uri("/cars/{name}", "prius")
				.exchange().expectStatus().isOk()
				.expectBody()
					.jsonPath("name").isEqualTo("prius")
					.jsonPath("type").isEqualTo("hybrid");
    }

}
