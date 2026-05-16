package com.example.car.web;

import com.example.car.domain.Car;
import com.example.car.domain.CarRepository;
import com.example.car.web.exception.DomainExceptionWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@WebFluxTest(controllers = CarController.class)
class CarControllerTests {

    @MockitoBean
    private CarRepository carRepository;

    @MockitoBean
    private DomainExceptionWrapper domainExceptionWrapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getCar_WithName_returnsCar() {
    	Car car = new Car("prius", "hybrid");
		given(carRepository.findByName("prius")).willReturn(Mono.just(car));

		this.webTestClient.get().uri("/cars/{name}", "prius")
				.exchange().expectStatus().isOk()
				.expectBody()
					.jsonPath("name").isEqualTo("prius")
					.jsonPath("type").isEqualTo("hybrid");
    }

}
