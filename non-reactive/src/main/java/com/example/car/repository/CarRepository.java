package com.example.car.repository;

import com.example.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {

	Optional<Car> findByName(String name);

}
