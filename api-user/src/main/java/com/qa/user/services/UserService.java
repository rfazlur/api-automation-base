package com.qa.user.services;

import com.qa.core.service.BaseService;
import io.restassured.response.Response;

public class UserService extends BaseService {
    public Response getAllUsers() {
        return apiClient.get("/users");
    }

    public Response getUserById(int userId) {
        return apiClient.get("/users/"+userId);
    }

    public Response createUser(Object requestBody) {
        return apiClient.post("/users/add", requestBody);
    }

    public Response updateUser(int userId, Object requestBody) {
        return apiClient.put("/users/"+userId, requestBody);
    }

    public Response deleteUser(int userId) {
        return apiClient.delete("/users/"+userId);
    }
}
