package com.example.car.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Car")
@Entity(name = "Car")
public class Car {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String type;

	public Car() {

	}

	public Car(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Car{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
