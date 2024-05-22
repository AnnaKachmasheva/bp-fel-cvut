package cz.cvut.fel.bp.user.rest;

import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.rest.MeApi;
import cz.cvut.fel.bp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author annak
 * @since 2024-03-14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class CurrentUserController implements MeApi {

    private final UserService userService;


    @Override
    public ResponseEntity<User> getCurrentUser() {

        log.info("Get current user.");

        final User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(currentUser);
    }
}
