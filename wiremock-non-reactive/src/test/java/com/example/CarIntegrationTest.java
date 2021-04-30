package com.example;

import com.example.data.FakeDataGenerator;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { WireMockInitializer.class }, classes = {
        CarApplication.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarIntegrationTest {

    @Autowired
    private WireMockServer wireMockServer;

    @LocalServerPort
    private int localServerPort;

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @AfterEach
    public void afterEach()
    {
        this.wireMockServer.resetAll();
    }

    @Test
    void testGetCars() throws IOException {
        wireMockServer.stubFor(get(WireMock.urlEqualTo("/cars/BMW"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("content-type", "application/json")
                        .withBody(FakeDataGenerator.getFakeCarsAsJSON())));

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
