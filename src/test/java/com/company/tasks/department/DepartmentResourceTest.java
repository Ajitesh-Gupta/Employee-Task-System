package com.company.tasks.department;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(DepartmentResource.class)
public class DepartmentResourceTest {

    private Map<String, Object> newDepartmentPayload(String uniqueName) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", uniqueName);
        body.put("description", "This is a test department.");
        return body;
    }

    @Test
    @DisplayName("List all departments")
    void listAllDepartments() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("name", hasItem("Engineering"));
    }

    @Test
    @DisplayName("Get department by id")
    void getDepartmentById_found() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("Engineering"));
    }

    @Test
    @DisplayName("Get department by id - not found")
    void getDepartmentById_notFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/99999")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Create department - happy path")
    void createDepartment() {
        String name = "Test Dept " + UUID.randomUUID();

        given()
            .contentType(ContentType.JSON)
            .body(newDepartmentPayload(name))
        .when()
            .post()
        .then()
            .statusCode(201)
            .header("Location", containsString("/api/departments/"))
            .body("id", notNullValue())
            .body("name", equalTo(name))
            .body("description", equalTo("This is a test department."));
    }

    @Test
    @DisplayName("Get department employees")
    void listDepartmentEmployees() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/1/employees")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("email", hasItem("john.smith@company.com"));
    }

    @Test
    @DisplayName("Get department employees for department - not found")
    void listDepartmentEmployees_notFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/99999/employees")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Get department tasks")
    void listDepartmentTasks() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/1/tasks")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Get department tasks for department - not found")
    void listDepartmentTasks_notFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/99999/tasks")
        .then()
            .statusCode(404);
    }
}

