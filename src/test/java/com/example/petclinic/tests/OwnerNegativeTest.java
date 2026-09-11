package com.example.petclinic.tests;

import com.example.petclinic.base.BaseTest;
import com.example.petclinic.dto.OwnerDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("PetClinic API")
@Feature("Owner negative cases")
public class OwnerNegativeTest extends BaseTest {

    @Test
    @Story("POST /api/owners with invalid body")
    @DisplayName("Create owner with empty required fields returns validation error")
    @Severity(SeverityLevel.CRITICAL)
    void createOwnerWithEmptyFields() {
        OwnerDto invalid = new OwnerDto();
        invalid.setFirstName("");
        invalid.setLastName("");
        invalid.setAddress("");
        invalid.setCity("");
        invalid.setTelephone("");

        given()
                .spec(spec)
                .body(invalid)
                .when()
                .post("/api/owners")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("error", notNullValue())
                .body("message", notNullValue())
                .body("path", equalTo("/api/owners"));
    }

    @Test
    @Story("POST /api/owners with null body")
    @DisplayName("Create owner with null fields returns validation error")
    void createOwnerWithNullFields() {
        OwnerDto invalid = new OwnerDto(); // все поля null

        given()
                .spec(spec)
                .body(invalid)
                .when()
                .post("/api/owners")
                .then()
                .statusCode(400)
                .body("status", equalTo(400));
    }
}