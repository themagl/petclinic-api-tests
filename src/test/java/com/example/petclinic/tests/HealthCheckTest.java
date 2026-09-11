package com.example.petclinic.tests;

import com.example.petclinic.base.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("PetClinic API")
@Feature("Health check")
public class HealthCheckTest extends BaseTest {

    @Test
    @Story("GET /actuator/health")
    @DisplayName("Health endpoint returns UP")
    @Severity(SeverityLevel.BLOCKER)
    void healthShouldBeUp() {
        given()
                .spec(spec)
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}