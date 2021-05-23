package com.example.car.web;

import com.example.car.domain.Car;
import com.example.car.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarsController.class)
class CarsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CarService carService;

	private final Car car = new Car("prius", "hybrid");

	@Test
	void getCar_WithName_ReturnsCar() throws Exception {
		given(this.carService.getCarDetails("prius")).willReturn(car);
		this.mockMvc.perform(get("/cars/{name}", "prius")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("prius"))
				.andExpect(jsonPath("type").value("hybrid"));
	}

	@Test
	void getCar_NotFound_Returns404() throws Exception {
		given(this.carService.getCarDetails("prius")).willThrow(new CarNotFoundException());
		this.mockMvc.perform(get("/cars/{name}", "prius")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void saveCar_WithName_ReturnsCar() throws Exception {
		given(this.carService.saveCar(any(Car.class)))
				.willReturn(car);
		this.mockMvc.perform(post("/cars/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(car)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("location"));
	}

}