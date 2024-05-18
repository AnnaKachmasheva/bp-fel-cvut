package cz.cvut.fel.bp.user.service.impl;

import cz.cvut.fel.bp.exceptions.NotFoundException;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.model.CustomUserDetails;
import cz.cvut.fel.bp.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author annak
 * @since 2024-03-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = repository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User with email={" + email + "} not found."));

        log.debug("User Authenticated Successfully..!!!");
        UserDetails userDetails = new CustomUserDetails(user);
        log.debug("UserDetails={}", userDetails);
        return userDetails;
    }

}
