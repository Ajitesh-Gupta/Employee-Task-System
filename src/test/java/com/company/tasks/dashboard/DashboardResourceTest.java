package com.company.tasks.dashboard;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(DashboardResource.class)
public class DashboardResourceTest {

    @Test
    @DisplayName("Dashboard stats - all metrics")
    void getDashboardStats() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/stats")
        .then()
            .statusCode(200)
            .body("statusCounts", notNullValue())
            .body("completionMetrics", notNullValue())
            .body("totalEmployees", greaterThan(0))
            .body("averageCompletionTimeHours", notNullValue());
    }

    @Test
    @DisplayName("Dashboard stats - status counts")
    void getDashboardStats_statusCounts() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/stats")
        .then()
            .statusCode(200)
            .body("statusCounts.TODO", notNullValue())
            .body("statusCounts.IN_PROGRESS", notNullValue())
            .body("statusCounts.DONE", notNullValue());
    }

    @Test
    @DisplayName("Top employees")
    void getTopEmployees_defaultLimit() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/employees/top")
        .then()
            .statusCode(200)
            .body("size()", lessThanOrEqualTo(5));
    }

    @Test
    @DisplayName("Get overdue tasks")
    void getOverdueTasks() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/tasks/overdue")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }
}

