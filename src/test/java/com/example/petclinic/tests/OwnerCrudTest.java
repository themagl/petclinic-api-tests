package com.example.petclinic.tests;

import com.example.petclinic.base.BaseTest;
import com.example.petclinic.dto.OwnerDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("PetClinic API")
@Feature("Owner CRUD")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OwnerCrudTest extends BaseTest {

    private static Integer ownerId;

    @Test
    @Order(1)
    @Story("POST /api/owners")
    @DisplayName("Create owner")
    @Severity(SeverityLevel.CRITICAL)
    void createOwner() {
        OwnerDto newOwner = new OwnerDto(
                "John", "Doe", "123 Main St", "Springfield", "1234567890"
        );

        OwnerDto created = given()
                .spec(spec)
                .body(newOwner)
                .when()
                .post("/api/owners")
                .then()
                .statusCode(201)
                .extract()
                .as(OwnerDto.class);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getFirstName()).isEqualTo("John");
        assertThat(created.getLastName()).isEqualTo("Doe");
        ownerId = created.getId();
    }

    @Test
    @Order(2)
    @Story("GET /api/owners/{id}")
    @DisplayName("Get owner by id")
    void getOwner() {
        OwnerDto owner = given()
                .spec(spec)
                .pathParam("id", ownerId)
                .when()
                .get("/api/owners/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(OwnerDto.class);

        assertThat(owner.getId()).isEqualTo(ownerId);
        assertThat(owner.getFirstName()).isEqualTo("John");
    }

    @Test
    @Order(3)
    @Story("PUT /api/owners/{id}")
    @DisplayName("Update owner")
    void updateOwner() {
        OwnerDto updated = new OwnerDto(
                "Jane", "Smith", "456 Oak Ave", "Shelbyville", "9876543210"
        );
        updated.setId(ownerId);

        given()
                .spec(spec)
                .pathParam("id", ownerId)
                .body(updated)
                .when()
                .put("/api/owners/{id}")
                .then()
                .statusCode(204);

        OwnerDto fetched = given()
                .spec(spec)
                .pathParam("id", ownerId)
                .when()
                .get("/api/owners/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(OwnerDto.class);

        assertThat(fetched.getFirstName()).isEqualTo("Jane");
        assertThat(fetched.getLastName()).isEqualTo("Smith");
        assertThat(fetched.getCity()).isEqualTo("Shelbyville");
    }

    @Test
    @Order(4)
    @Story("DELETE /api/owners/{id}")
    @DisplayName("Delete owner")
    void deleteOwner() {
        given()
                .spec(spec)
                .pathParam("id", ownerId)
                .when()
                .delete("/api/owners/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    @Story("GET /api/owners/{id}")
    @DisplayName("Deleted owner is not accessible")
    void deletedOwnerNotFound() {
        given()
                .spec(spec)
                .pathParam("id", ownerId)
                .when()
                .get("/api/owners/{id}")
                .then()
                .statusCode(404);
    }
}