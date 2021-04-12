package com.example.service;

import com.example.domain.Car;
import com.example.util.CarApplicationProperties;
import com.example.util.CarNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CarService {

    private final RestTemplate restTemplate;
    private final CarApplicationProperties applicationProperties;

    public CarService(RestTemplate restTemplate, CarApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    public Car getCarDetails(String name) {
        ResponseEntity<Car> responseEntity = this.restTemplate
                .exchange(applicationProperties.getUrl() + "/cars/" + name, HttpMethod.GET, null, Car.class);
        return Optional.of(responseEntity)
                .filter(val -> val.getStatusCode() == HttpStatus.OK)
                .map(HttpEntity::getBody)
                .orElseThrow(CarNotFoundException::new);
    }
}
