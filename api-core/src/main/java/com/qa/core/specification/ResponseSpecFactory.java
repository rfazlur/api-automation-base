package com.qa.core.specification;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecFactory {
    private ResponseSpecFactory() {
    }

    public static ResponseSpecification success(
            int expectedStatus
    ) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatus)
                .build();
    }

    public static ResponseSpecification success(
            int expectedStatus,
            long maxResponseTimeMs
    ) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatus)
                .expectResponseTime(org.hamcrest.Matchers.lessThan(maxResponseTimeMs))
                .build();
    }

    public static ResponseSpecification jsonResponse(
            int expectedStatus
    ) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatus)
                .expectContentType("application/json")
                .build();
    }
}
