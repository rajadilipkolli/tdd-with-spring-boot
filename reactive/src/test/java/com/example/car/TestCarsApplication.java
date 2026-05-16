package com.example.car;

import org.springframework.boot.SpringApplication;
import com.example.car.common.ContainersConfig;

public class TestCarsApplication {
    

    public static void main(String[] args) {
        SpringApplication.from(CarsApplication::main).with(ContainersConfig.class).run(args);
    }
}
