package cz.cvut.fel.bp.mapper;

import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;
import cz.cvut.fel.bp.generator.GeneratorUserEntity;
import cz.cvut.fel.bp.user.mapper.UserEntity2UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author annak
 * @since 2024-03-16
 */
class UserEntity2UserMapperTest {

    private static UserEntity2UserMapper userEntity2UserMapper;


    @BeforeAll
    static void setup() {
        userEntity2UserMapper = new UserEntity2UserMapper();
    }

    @Test
    void toUser_null_null() {
        User user = userEntity2UserMapper.toUser(null);

        Assertions.assertNull(user);
    }


    @Test
    void toUser_UserEntity_User() {
        // entity
        UserEntity userEntity = GeneratorUserEntity.generateUserEntity();

        // map
        User user = userEntity2UserMapper.toUser(userEntity);

        Assertions.assertEquals(userEntity.getId(), user.getId());
        Assertions.assertEquals(userEntity.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), user.getLastName());
        Assertions.assertEquals(userEntity.getEmail(), user.getEmail());
        Assertions.assertEquals(userEntity.getRole(), UserRole.fromString(user.getUserRole().name()));
    }
}