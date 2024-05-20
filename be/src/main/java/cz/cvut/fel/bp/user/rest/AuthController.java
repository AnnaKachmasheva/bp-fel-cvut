package cz.cvut.fel.bp.user.rest;

import cz.cvut.fel.bp.api.v1.model.LogIn;
import cz.cvut.fel.bp.api.v1.model.LoginToken;
import cz.cvut.fel.bp.api.v1.model.Registration;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.rest.AuthApi;
import cz.cvut.fel.bp.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://master.d3f81l92tk91tc.amplifyapp.com", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AuthController implements AuthApi {

    private final UserAuthService userAuthService;


    @Override
    public ResponseEntity<LoginToken> login(@RequestBody LogIn logIn) {

        log.info("New log in request={}.", logIn);

        final LoginToken token = userAuthService.login(logIn);

        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<User> registration(@RequestBody Registration registration) {

        log.info("New registration request={}.", registration);

        final User user = userAuthService.registration(registration);

        return ResponseEntity.ok(user);
    }

}