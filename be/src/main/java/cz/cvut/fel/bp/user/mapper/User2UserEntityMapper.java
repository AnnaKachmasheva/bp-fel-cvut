package cz.cvut.fel.bp.user.mapper;

import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class User2UserEntityMapper {

    public UserEntity toUserEntity(User user) {
        if (user == null) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setEmail(user.getEmail());
        userEntity.setRole(UserRole.valueOf(user.getUserRole().toString()));
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setDeleted((boolean)user.getIsDeleted());

        return userEntity;
    }

}
