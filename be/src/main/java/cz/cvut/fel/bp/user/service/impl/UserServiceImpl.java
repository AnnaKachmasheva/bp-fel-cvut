package cz.cvut.fel.bp.user.service.impl;

import cz.cvut.fel.bp.api.v1.model.ChangePassword;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.common.utils.Utils;
import cz.cvut.fel.bp.exceptions.NotFoundException;
import cz.cvut.fel.bp.exceptions.PasswordNotMatchesException;
import cz.cvut.fel.bp.exceptions.PermissionDeniedException;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;
import cz.cvut.fel.bp.user.mapper.UserEntity2UserMapper;
import cz.cvut.fel.bp.user.model.CustomUserDetails;
import cz.cvut.fel.bp.user.repository.UserEntityRepository;
import cz.cvut.fel.bp.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


/**
 * @author annak
 * @since 2024-03-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserEntityRepository userEntityRepository;

    private final UserEntity2UserMapper userEntity2UserMapper;


    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        Utils.nonNull(userId);
        checkPermissionAndUpdateUser(userId);

        final UserEntity userEntity = getUserEntityById(userId);

        // user deleted ?
        if (userEntity.isDeleted()) {
            log.debug("User with email={} is already deleted.", userEntity.getEmail());
            return;
        }

        log.debug("Before deletion user={}.", userEntity);

        userEntity.setDeleted(true); // delete user
        userEntity.setUpdatedAt(LocalDateTime.now());
        UserEntity deletedUserEntity = userEntityRepository.save(userEntity);
        log.debug("After deletion user ={}..", deletedUserEntity);
    }

    @Override
    @Transactional
    public User getUserById(UUID userId) {
        Utils.nonNull(userId);

        final UserEntity userEntity = getUserEntityById(userId);

        log.debug("Found by id={} userEntity={}.", userId, userEntity);

        return userEntity2UserMapper.toUser(userEntity);
    }

    @Override
    @Transactional
    public User updateUser(UUID userId,
                           User user) {
        Utils.nonNull(userId);
        checkPermissionAndUpdateUser(userId);

        final UserEntity userEntity = getUserEntityById(userId);
        log.debug("Before update user={}.", userEntity);

        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setDeleted(user.getIsDeleted());

        if (currentUserIsAdmin()) {
            userEntity.setRole(UserRole.valueOf(user.getUserRole().toString()));
        }

        // save to DB
        final UserEntity savedUserEntity = userEntityRepository.save(userEntity);

        log.debug("After update user={}.", userEntity);

        return userEntity2UserMapper.toUser(savedUserEntity);
    }

    @Override
    @Transactional
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Current user authentication={}.", authentication);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        log.debug("Current user principal={}.", principal);
        return userEntity2UserMapper.toUser(principal.getUserEntity());
    }

    @Override
    @Transactional
    public void changePassword(UUID userId,
                               ChangePassword changePassword) {
        Utils.nonNull(userId);
        checkPermissionAndUpdateUser(userId);

        log.debug("Change password={} by user with id={} .", changePassword, userId);
        String oldPassword = changePassword.getPasswordOld();

        UserEntity userEntity = getUserEntityById(userId);
        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new PasswordNotMatchesException("BAD_OLD_PASSWORD", "Old password isn't valid");
        }
        log.info("Password are same");

        String newPassword = changePassword.getPasswordNew();
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        log.debug("Update password fpr user={}", userEntity);
        userEntityRepository.save(userEntity);
    }


    private UserEntity getUserEntityById(UUID id) {
        Utils.nonNull(id);

        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(id);
        if (userEntityOptional.isEmpty())
            throw new NotFoundException("USER_NOT_FOUND", "User with id={" + id + "} not found.");

        return userEntityOptional.get();
    }


    private void checkPermissionAndUpdateUser(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Current user authentication={}.", authentication);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        log.debug("Current user principal={}.", principal);
        UserEntity currentUserEntity = principal.getUserEntity();
        if (!currentUserEntity.isAdmin() && !(userId.equals(currentUserEntity.getId()))) {
            log.debug("User={} cannot permission update user={}.", currentUserEntity, userId);
            throw new PermissionDeniedException(
                    "PERMISSION_DENIED",
                    "Current user " + currentUserEntity + " doesn't have permission to change another user with id" + userId);
        }
    }


    private boolean currentUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Current user authentication={}.", authentication);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        log.debug("Current user principal={}.", principal);
        UserEntity currentUserEntity = principal.getUserEntity();
        return currentUserEntity.isAdmin();
    }
}
