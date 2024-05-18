package cz.cvut.fel.bp.user.mapper;

import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.model.UserRole;
import cz.cvut.fel.bp.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class UserEntity2UserMapper {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public User toUser(UserEntity userEntity) {
        if (userEntity == null) return null;

        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setUserRole(UserRole.valueOf(userEntity.getRole().toString()));
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.isDeleted(userEntity.isDeleted());
        user.setUpdated(userEntity.getUpdatedAt().format(formatter));

        return user;
    }

    public List<User> toUserList(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            User user = toUser(userEntity);
            users.add(user);
        }

        return users;
    }

}
