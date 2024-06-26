package com.example.car.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.StringJoiner;

@Document
public class Car {

    @Id
    private String id;

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

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "{", "}")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .toString();
    }
}
