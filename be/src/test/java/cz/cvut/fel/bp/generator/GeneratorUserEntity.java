package cz.cvut.fel.bp.generator;

import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;

import java.util.Random;

/**
 * @author annak
 * @since 2024-03-16
 */
public class GeneratorUserEntity {

    public static UserEntity generateUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Common.generateRandomId());
        userEntity.setEmail(Common.generateRandomEmail());
        userEntity.setRole(generateRandomUserRole());
        userEntity.setFirstName(Common.generateRandomString(6));
        userEntity.setLastName(Common.generateRandomString(6));
        userEntity.setCreatedAt(Common.generateRandomDate());
        userEntity.setUpdatedAt(Common.generateRandomDate());
        return userEntity;
    }

    private static UserRole generateRandomUserRole() {
        return UserRole.values()[new Random().nextInt(UserRole.values().length)];
    }

}