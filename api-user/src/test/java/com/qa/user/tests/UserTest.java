package com.qa.user.tests;

import com.qa.user.services.UserService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.qa.core.specification.ResponseSpecFactory.jsonResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private UserService userService;

    @BeforeClass
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void shouldGetAllUsersSuccessfully() {

        Response response = userService.getAllUsers();

        response.then().spec(jsonResponse(200));

        assertThat(response.jsonPath().getInt("total"))
                .isPositive();

        assertThat(response.jsonPath().getList("users"))
                .isNotEmpty();

        assertThat(response.jsonPath().getInt("limit"))
                .isPositive();
    }

    @Test
    public void shouldGetUserByIdSuccessfully() {

        Response response = userService.getUserById(1);

        response.then().spec(jsonResponse(200));

        assertThat(response.jsonPath().getInt("id"))
                .isEqualTo(1);

        assertThat(response.jsonPath().getString("firstName"))
                .isNotBlank();

        assertThat(response.jsonPath().getString("email"))
                .contains("@");

        assertThat(response.jsonPath().getString("phone"))
                .isNotBlank();
    }

    @Test
    public void shouldReturnNotFoundForInvalidUser() {

        Response response = userService.getUserById(999999);

        assertThat(response.statusCode())
                .isEqualTo(404);
    }
}
