package com.company.tasks.task;

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
@TestHTTPEndpoint(TaskResource.class)
public class TaskResourceTest {

    private Map<String, Object> newTaskPayload(String uniqueTitle) {
        Map<String, Object> body = new HashMap<>();
        body.put("title", uniqueTitle);
        body.put("description", "This is a test task.");
        body.put("status", "TODO");
        body.put("priority", "MEDIUM");
        body.put("assignedEmployeeId", 1);
        return body;
    }

    @Test
    @DisplayName("List all tasks")
    void listAllTasks() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Get task by id")
    void getTaskById_found() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("title", notNullValue());
    }

    @Test
    @DisplayName("Get task by id - not found")
    void getTaskById_notFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/99999")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Create task - happy path")
    void createTask() {
        String title = "Test Task " + UUID.randomUUID();

        given()
            .contentType(ContentType.JSON)
            .body(newTaskPayload(title))
        .when()
            .post()
        .then()
            .statusCode(201)
            .header("Location", containsString("/api/tasks/"))
            .body("id", notNullValue())
            .body("title", equalTo(title))
            .body("status", equalTo("TODO"));
    }

    @Test
    @DisplayName("Validation failure - missing required fields")
    void createTask_validationFailure() {
        Map<String, Object> bad = new HashMap<>();
        bad.put("description", "Missing title field");

        given()
            .contentType(ContentType.JSON)
            .body(bad)
        .when()
            .post()
        .then()
            .statusCode(anyOf(is(400), is(422)));
    }

    @Test
    @DisplayName("Update task - changes title")
    void updateTask() {
        String title = "Update Task " + UUID.randomUUID();
        int newId = given()
            .contentType(ContentType.JSON)
            .body(newTaskPayload(title))
        .when()
            .post()
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        Map<String, Object> update = new HashMap<>();
        update.put("title", "Updated Title " + UUID.randomUUID());
        update.put("description", "Updated description");
        update.put("status", "IN_PROGRESS");
        update.put("priority", "HIGH");
        update.put("assignedEmployeeId", 1);

        given()
            .contentType(ContentType.JSON)
            .body(update)
        .when()
            .put("/" + newId)
        .then()
            .statusCode(200)
            .body("title", containsString("Updated Title"));
    }

    @Test
    @DisplayName("Delete task")
    void deleteTask() {
        String title = "Delete Task " + UUID.randomUUID();
        int newId = given()
            .contentType(ContentType.JSON)
            .body(newTaskPayload(title))
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
    @DisplayName("Update task status")
    void updateTaskStatus() {
        given()
            .contentType(ContentType.JSON)
            .body("\"IN_PROGRESS\"")
        .when()
            .put("/4/status")
        .then()
            .statusCode(200)
            .body("status", equalTo("IN_PROGRESS"));
    }

    @Test
    @DisplayName("Assign task to employee")
    void assignTask() {
        String title = "Assign Task " + UUID.randomUUID();
        int newId = given()
            .contentType(ContentType.JSON)
            .body(newTaskPayload(title))
        .when()
            .post()
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .contentType(ContentType.JSON)
            .body("2")
        .when()
            .put("/" + newId + "/assign")
        .then()
            .statusCode(200)
            .body("assignedEmployeeId", equalTo(2));
    }

    @Test
    @DisplayName("Filter tasks by status")
    void filterByStatus() {
        given()
            .accept(ContentType.JSON)
            .queryParam("status", "TODO")
        .when()
            .get("/status")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("status", everyItem(equalTo("TODO")));
    }

    @Test
    @DisplayName("Filter tasks by priority")
    void filterByPriority() {
        given()
            .accept(ContentType.JSON)
            .queryParam("priority", "HIGH")
        .when()
            .get("/priority")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("priority", everyItem(equalTo("HIGH")));
    }

    @Test
    @DisplayName("Get overdue tasks")
    void getOverdueTasks() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/overdue")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }
}
