package cz.cvut.fel.bp.user.mapper;

import cz.cvut.fel.bp.api.v1.model.NewUser;
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
public class NewUser2UserEntityMapper {

    private final PasswordEncoder passwordEncoder;


    public UserEntity toUserEntity(NewUser newUser) {
        if (newUser == null) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(newUser.getEmail());
        userEntity.setLastName(newUser.getLastName());
        userEntity.setFirstName(newUser.getFirstName());
        userEntity.setFirstName(newUser.getFirstName());
        userEntity.setRole(UserRole.valueOf(newUser.getUserRole().toString()));
        userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setDeleted(false);

        return userEntity;
    }

}
