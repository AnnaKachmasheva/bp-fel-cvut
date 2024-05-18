package cz.cvut.fel.bp.user.service.impl;

import cz.cvut.fel.bp.api.v1.model.NewUser;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.model.UserPage;
import cz.cvut.fel.bp.exceptions.AlreadyExistException;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.mapper.NewUser2UserEntityMapper;
import cz.cvut.fel.bp.user.mapper.UserEntity2UserMapper;
import cz.cvut.fel.bp.user.repository.UserEntityRepository;
import cz.cvut.fel.bp.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author annak
 * @since 2024-03-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserEntityRepository userEntityRepository;

    private final UserEntity2UserMapper userEntity2UserMapper;
    private final NewUser2UserEntityMapper newUser2UserEntityMapper;


    @Override
    public void createUser(NewUser newUser) {
        // checking if the user already exists. The email is unique for each user
        final String email = newUser.getEmail();
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmailIgnoreCase(email);
        if (userEntityOptional.isPresent()) {
            throw new AlreadyExistException("USER_ALREADY_EXISTS",
                    "User with email={" + email + "} is already exists.");
        }
        log.info("User by email={} not found.", email);

        UserEntity userEntity = newUser2UserEntityMapper.toUserEntity(newUser);
        log.info("Save new userEntity={}.", userEntity);

        UserEntity savedUserEntity = userEntityRepository.save(userEntity);
        log.info("Saved userEntity={}.", savedUserEntity);
    }

    @Override
    public UserPage getUsers(Pageable pageable) {

        Page<UserEntity> userEntitiesPage = userEntityRepository.findAll(pageable);

        log.info("Found {} users={} .", userEntitiesPage.getNumberOfElements(), userEntitiesPage.getContent());

        List<User> users = userEntity2UserMapper.toUserList(userEntitiesPage.getContent());

        UserPage userPage = new UserPage();
        userPage.setContent(users);
        userPage.setTotalPages(userEntitiesPage.getTotalPages());
        userPage.setTotalElements(userEntitiesPage.getTotalElements());
        userPage.setSize(userEntitiesPage.getSize());
        userPage.setPageable(userEntitiesPage.getPageable());
        userPage.setNumberOfElements(userEntitiesPage.getNumberOfElements());
        userPage.setNumber(userEntitiesPage.getNumber());
        userPage.setEmpty(userEntitiesPage.isEmpty());
        userPage.setFirst(userEntitiesPage.isFirst());

        log.info("Found page={}.", userPage);

        return userPage;
    }

}
