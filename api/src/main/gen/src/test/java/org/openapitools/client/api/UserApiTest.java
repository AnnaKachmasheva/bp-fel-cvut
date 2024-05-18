/*
 * BP API
 * Inventory management system API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.NewUser;
import org.openapitools.client.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UserApi
 */
@Disabled
public class UserApiTest {

    private final UserApi api = new UserApi();

    /**
     * Create new user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createUserByTest() throws ApiException {
        NewUser newUser = null;
        List<User> response = api.createUserBy(newUser);
        // TODO: test validations
    }

    /**
     * Delete user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteUserByTest() throws ApiException {
        Integer userId = null;
        api.deleteUserBy(userId);
        // TODO: test validations
    }

    /**
     * Get user by id
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUserByIdTest() throws ApiException {
        Integer userId = null;
        User response = api.getUserById(userId);
        // TODO: test validations
    }

    /**
     * Get users
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUsersByTest() throws ApiException {
        List<User> response = api.getUsersBy();
        // TODO: test validations
    }

    /**
     * Log out
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void logoutTest() throws ApiException {
        Object body = null;
        api.logout(body);
        // TODO: test validations
    }

    /**
     * Update user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateUserTest() throws ApiException {
        Integer userId = null;
        User user = null;
        User response = api.updateUser(userId, user);
        // TODO: test validations
    }

}