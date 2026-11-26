package com.qa.core.client;

import com.qa.core.exceptions.ApiFrameworkException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    private final RequestSpecification requestSpec;

    public ApiClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response get(String endpoint) {
        return execute("GET", endpoint, null);
    }

    public Response post(String endpoint, Object body) {
        return execute("POST", endpoint, body);
    }

    public Response put(String endpoint, Object body) {
        return execute("PUT", endpoint, body);
    }

    public Response patch(String endpoint, Object body) {
        return execute("PATCH", endpoint, body);
    }

    public Response delete(String endpoint) {
        return execute("DELETE", endpoint, null);
    }

    private Response execute(String method, String endpoint, Object body) {
        long startTime = System.currentTimeMillis();

        try {
            log.info(
                    "Executing API request | Method: {} | Endpoint: {}",
                    method,
                    endpoint
            );

            Response response;
            switch (method) {
                case "GET":
                    response = given()
                            .spec(requestSpec)
                            .when()
                            .get(endpoint);
                    break;
                case "POST":
                    response = given()
                            .spec(requestSpec)
                            .body(body)
                            .when()
                            .post(endpoint);
                    break;
                case "PUT":
                    response = given()
                            .spec(requestSpec)
                            .body(body)
                            .when()
                            .put(endpoint);
                    break;
                case "PATCH":
                    response = given()
                            .spec(requestSpec)
                            .body(body)
                            .when()
                            .patch(endpoint);
                    break;
                case "DELETE":
                    response = given()
                            .spec(requestSpec)
                            .when()
                            .delete(endpoint);
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Unsupported HTTP method: " + method
                    );
            }
            long duration = System.currentTimeMillis() - startTime;
            log.info(
                    "API response received | Method: {} | "
                            + "Endpoint: {} | Status: {} | "
                            + "Duration: {} ms",
                    method,
                    endpoint,
                    response.statusCode(),
                    duration
            );
            return response;

        } catch (IllegalArgumentException e) {
            long duration =
                    System.currentTimeMillis() - startTime;

            log.error(
                    "API request execution failed | "
                            + "Method: {} | Endpoint: {} | "
                            + "Duration: {} ms",
                    method,
                    endpoint,
                    duration,
                    e
            );

            throw new ApiFrameworkException(
                    "Failed to execute "
                            + method
                            + " request to "
                            + endpoint,
                    e
            );
        }

    }


}
