package com.company.tasks.employee;

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
@TestHTTPEndpoint(EmployeeResource.class)
public class EmployeeResourceTest {

    private Map<String, Object> newEmployeePayload(String uniqueEmail) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Test User" );
        body.put("email", uniqueEmail);
        body.put("department", "Engineering");
        body.put("position", "QA Engineer");
        return body;
    }

    @Test
    @DisplayName("List all employees")
    void listAllEmployees() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("email", hasItem("john.smith@company.com"));
    }

    @Test
    @DisplayName("Get existing employee by id")
    void getEmployeeById_found() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("email", equalTo("john.smith@company.com"));
    }

    @Test
    @DisplayName("Get employee by id - not found")
    void getEmployeeById_notFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/99999")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Create employee - happy path")
    void createEmployee() {
        String email = "test" + UUID.randomUUID() + "@company.com";

        given()
            .contentType(ContentType.JSON)
            .body(newEmployeePayload(email))
        .when()
            .post()
        .then()
            .statusCode(201)
            .header("Location", containsString("/api/employees/"))
            .body("id", notNullValue())
            .body("email", equalTo(email))
            .body("department", equalTo("Engineering"));
    }

    @Test
    @DisplayName("Validation failure - missing required fields")
    void createEmployee_validationFailure() {
        Map<String, Object> bad = new HashMap<>();
        bad.put("email", "invalid.missing.name@company.com");
        bad.put("department", "Engineering");

        given()
            .contentType(ContentType.JSON)
            .body(bad)
        .when()
            .post()
        .then()
            .statusCode(anyOf(is(400), is(422)));
    }

    @Test
    @DisplayName("Update employee - changes name")
    void updateEmployee() {
        String email = "update" + UUID.randomUUID() + "@company.com";
        int newId = given()
            .contentType(ContentType.JSON)
            .body(newEmployeePayload(email))
        .when()
            .post()
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        Map<String, Object> update = new HashMap<>();
        update.put("name", "Updated Name");
        update.put("email", email);
        update.put("department", "Engineering");

        given()
            .contentType(ContentType.JSON)
            .body(update)
        .when()
            .put("/" + newId)
        .then()
            .statusCode(200)
            .body("name", equalTo("Updated Name"));
    }

    @Test
    @DisplayName("Delete employee without active tasks")
    void deleteEmployee() {
        String email = "delete" + UUID.randomUUID() + "@company.com";
        int newId = given()
            .contentType(ContentType.JSON)
            .body(newEmployeePayload(email))
        .when()
            .post()
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .accept(ContentType.JSON)
        .when()
            .delete("/" + newId)
        .then()
            .statusCode(204);

        given()
            .accept(ContentType.JSON)
        .when()
            .get("/" + newId)
        .then()
            .statusCode(404);
    }
    
    @Test
    @DisplayName("Get employee assigned tasks")
    void getEmployeeTasks() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/1/tasks")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("assignedEmployeeId", everyItem(equalTo(1)));
    }

    @Test
    @DisplayName("Get tasks for non-existent employee")
    void getEmployeeTasks_notFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/99999/tasks")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Search by department - Parameter missing")
    void searchByDepartment_missingParam() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/search/department")
        .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Search employees by department")
    void searchByDepartment() {
        given()
            .accept(ContentType.JSON)
            .queryParam("department", "Engineering")
        .when()
            .get("/search/department")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("email", hasItem("john.smith@company.com"));
    }
}
