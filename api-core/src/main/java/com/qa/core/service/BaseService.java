package com.qa.core.service;

import com.qa.core.client.ApiClient;
import com.qa.core.specification.RequestSpecFactory;
import io.restassured.response.Response;

public class BaseService {
    protected final ApiClient apiClient;

    protected BaseService() {
        this.apiClient = new ApiClient(
                RequestSpecFactory.getDefaultSpec()
        );
    }

    protected Response get(String endpoint) {
        return apiClient.get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return apiClient.post(endpoint, body);
    }

    protected Response put(String endpoint, Object body) {
        return apiClient.put(endpoint, body);
    }

    protected Response patch(String endpoint, Object body) {
        return apiClient.patch(endpoint, body);
    }

    protected Response delete(String endpoint) {
        return apiClient.delete(endpoint);
    }
}
