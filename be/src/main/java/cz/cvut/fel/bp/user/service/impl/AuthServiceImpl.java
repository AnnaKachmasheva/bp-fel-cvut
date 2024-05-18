package cz.cvut.fel.bp.user.service.impl;

import cz.cvut.fel.bp.api.v1.model.*;
import cz.cvut.fel.bp.exceptions.AlreadyExistException;
import cz.cvut.fel.bp.exceptions.PasswordNotMatchesException;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.mapper.Registration2UserEntityMapper;
import cz.cvut.fel.bp.user.mapper.UserEntity2UserMapper;
import cz.cvut.fel.bp.user.model.CustomUserDetails;
import cz.cvut.fel.bp.user.repository.UserEntityRepository;
import cz.cvut.fel.bp.user.service.UserAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author annak
 * @since 2024-03-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserAuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final UserEntityRepository userEntityRepository;

    private final Registration2UserEntityMapper registration2userEntityMapper;
    private final UserEntity2UserMapper userEntity2UserMapper;

    @Override
    @Transactional
    public LoginToken login(LogIn logIn) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(logIn.getEmail());

        // checking if passwords are equal
        String password = logIn.getPassword();
        String originalPassword = ((CustomUserDetails) userDetails).getUserEntity().getPassword();
        String email = ((CustomUserDetails) userDetails).getEmail();
        if (!passwordEncoder.matches(password, originalPassword)) {
            log.info("Password matched successfully for email={}", email);
            throw new PasswordNotMatchesException("PASSWORDS_DONT_MATCH",
                    "User with email={" + email + "} has another password.");
        }
        log.info("Password are matches.");

        String jwtToken = jwtService.GenerateToken(userDetails);
        log.info("Create jwt token={}.", jwtToken);

        // create response token
        LoginToken loginToken = new LoginToken();
        loginToken.setEmail(userDetails.getUsername());
        loginToken.setUserRole(UserRole.fromValue(userDetails.getAuthorities().toArray()[0].toString()));
        loginToken.setToken(jwtToken);
        log.info("Create login token={}.", loginToken);

        return loginToken;
    }

    @Override
    @Transactional
    public User registration(Registration registration) {

        // checking if the user already exists. The email is unique for each user
        final String email = registration.getEmail();
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmailIgnoreCase(email);
        if (userEntityOptional.isPresent()) {
            throw new AlreadyExistException("USER_ALREADY_EXISTS",
                    "User with email={" + email + "} already exists.");
        }
        log.info("User by email={} not found.", email);

        UserEntity userEntity = registration2userEntityMapper.toUserEntity(registration);
        log.info("Save new userEntity={}.", userEntity);

        UserEntity savedUserEntity = userEntityRepository.save(userEntity);
        log.info("Saved userEntity={}.", savedUserEntity);

        return userEntity2UserMapper.toUser(savedUserEntity);
    }

}
