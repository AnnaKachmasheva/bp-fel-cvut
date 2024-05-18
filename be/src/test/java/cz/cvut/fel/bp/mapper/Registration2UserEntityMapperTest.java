package cz.cvut.fel.bp.mapper;

import cz.cvut.fel.bp.api.v1.model.Registration;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;
import cz.cvut.fel.bp.generator.GeneratorRegistrationRequest;
import cz.cvut.fel.bp.user.mapper.Registration2UserEntityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * @author annak
 * @since 2024-03-16
 */
class Registration2UserEntityMapperTest {

    private static Registration2UserEntityMapper registration2UserEntityMapper;
    private static PasswordEncoder passwordEncoder;


    @BeforeAll
    static void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
        registration2UserEntityMapper = new Registration2UserEntityMapper(passwordEncoder);
    }

    @Test
    void toUserEntity_null_null() {
        UserEntity userEntity = registration2UserEntityMapper.toUserEntity(null);

        Assertions.assertNull(userEntity);
    }


    @Test
    void toUserEntity_Registration_UserEntity() {
        Registration registration = GeneratorRegistrationRequest.generateRegistration();
        // must be for new registered user
        UserRole userRole = UserRole.ROLE_USER;
        boolean isDeleted = false;
        LocalDate date = LocalDate.now();

        UserEntity userEntity = registration2UserEntityMapper.toUserEntity(registration);

        Assertions.assertEquals(registration.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(registration.getFirstName(), userEntity.getFirstName());
        Assertions.assertEquals(registration.getLastName(), userEntity.getLastName());
        Assertions.assertEquals(userRole, userEntity.getRole());
        Assertions.assertEquals(isDeleted, userEntity.isDeleted());
        Assertions.assertTrue(passwordEncoder.matches(registration.getPassword(), userEntity.getPassword()));
        Assertions.assertEquals(date, userEntity.getCreatedAt());
        Assertions.assertEquals(date, userEntity.getUpdatedAt());
    }

}