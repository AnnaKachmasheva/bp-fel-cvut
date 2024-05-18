package cz.cvut.fel.bp.user.mapper;

import cz.cvut.fel.bp.api.v1.model.Registration;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class Registration2UserEntityMapper {

    private final PasswordEncoder passwordEncoder;


    public UserEntity toUserEntity(Registration registration) {
        if (registration == null) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registration.getEmail());
        userEntity.setLastName(registration.getLastName());
        userEntity.setFirstName(registration.getFirstName());
        userEntity.setFirstName(registration.getFirstName());
        userEntity.setPassword(passwordEncoder.encode(registration.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setRole(UserRole.ROLE_USER);
        userEntity.setDeleted(false);

        return userEntity;
    }

}
