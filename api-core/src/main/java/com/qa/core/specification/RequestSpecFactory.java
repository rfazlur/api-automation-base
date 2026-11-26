package com.qa.core.specification;

import com.qa.core.config.ConfigManager;
import com.qa.core.config.HeaderManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {
    private RequestSpecFactory() {
    }

    public static RequestSpecification getDefaultSpec() {
        int timeout = ConfigManager.getInt("api.timeout", 30000);

        RestAssuredConfig config =
                RestAssuredConfig.config()
                        .httpClient(
                                HttpClientConfig.httpClientConfig()
                                        .setParam("http.connection.timeout", timeout)
                                        .setParam("http.socket.timeout", timeout)
                        )
                        .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                        );

        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("base.url"))
                .addHeaders(HeaderManager.getDefaultHeaders())
                .setConfig(config)
                .build();
    }
}
