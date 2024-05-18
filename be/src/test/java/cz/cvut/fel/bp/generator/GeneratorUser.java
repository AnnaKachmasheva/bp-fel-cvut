package cz.cvut.fel.bp.generator;

import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.model.UserRole;

import java.util.Random;

/**
 * @author annak
 * @since 2024-03-16
 */
public class GeneratorUser {

    public static User generateUser() {
        User user = new User();
        user.setId(Common.generateRandomId());
        user.setEmail(Common.generateRandomEmail());
        user.setUserRole(generateRandomUserRole());
        user.setFirstName(Common.generateRandomString(6));
        user.setLastName(Common.generateRandomString(6));
        return user;
    }

    private static UserRole generateRandomUserRole() {
        return UserRole.values()[new Random().nextInt(UserRole.values().length)];
    }

}