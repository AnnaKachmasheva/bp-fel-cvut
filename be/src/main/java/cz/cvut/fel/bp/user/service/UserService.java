package cz.cvut.fel.bp.user.service;

import cz.cvut.fel.bp.api.v1.model.ChangePassword;
import cz.cvut.fel.bp.api.v1.model.User;

import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-14
 */
public interface UserService {


    /**
     * set isDelete = true for user with id
     *
     * @param userId unique user ID
     */
    void deleteUser(UUID userId);


    /**
     * @param userId unique user ID
     * @return User by id
     */
    User getUserById(UUID userId);


    /**
     * @param userId unique user ID that will be updated
     * @param user new update data
     * @return new parameters updated user data
     */
    User updateUser(UUID userId, User user);

    /**
     *
     * @return
     */
    User getCurrentUser();

    void changePassword(UUID userId, ChangePassword changePassword);

}
