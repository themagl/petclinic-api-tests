package com.example.petclinic.base;

import com.example.petclinic.config.RestAssuredConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static RequestSpecification spec;

    @BeforeAll
    static void globalSetup() {
        RestAssured.baseURI = System.getProperty(
                "baseUrl",
                "http://localhost:9966/petclinic"
        );
        spec = RestAssured.given()
                .filter(new AllureRestAssured())
                .log().ifValidationFails(LogDetail.ALL);
    }
}