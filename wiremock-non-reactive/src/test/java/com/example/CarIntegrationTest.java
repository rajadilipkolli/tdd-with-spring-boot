package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CarIntegrationTest {

    @LocalServerPort
    private Integer localServerPort;

    @Container
    static WireMockContainer wireMockServer = new WireMockContainer("wiremock/wiremock:3x-alpine")
            .withMapping("cars-by-name",
                    CarIntegrationTest.class,
                    "mocks-config.json");

    static {
        wireMockServer.start();
    }

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("com.example.car.url", wireMockServer::getBaseUrl);
    }

    @Test
    void testGetCars() throws IOException {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/cars/{name}", "BMW")
                .then()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .assertThat()
                .body("name", equalTo("BMW"))
                .body("type", equalTo("hybrid"));
    }

}
