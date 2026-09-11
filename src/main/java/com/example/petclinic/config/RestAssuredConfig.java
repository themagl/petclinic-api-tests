package com.example.petclinic.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public class RestAssuredConfig {

    public static final String BASE_URL = System.getProperty(
            "baseUrl",
            "http://localhost:9966/petclinic"
    );

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();
    }

    public static ResponseSpecification jsonResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}