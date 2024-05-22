package cz.cvut.fel.bp.user.rest;

import cz.cvut.fel.bp.api.v1.model.NewUser;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.model.UserPage;
import cz.cvut.fel.bp.api.v1.rest.UsersApi;
import cz.cvut.fel.bp.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author annak
 * @since 2024-03-14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class UsersController implements UsersApi {

    private final UsersService usersService;


    @Override
    public ResponseEntity<List<User>> createUser(NewUser newUser) {

        log.info("Create user. UserId={}.", newUser);

        usersService.createUser(newUser);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserPage> getUsers(Pageable pageable) {
        log.info("Get all users. {}.", pageable);

        return ResponseEntity.ok(usersService.getUsers(pageable));
    }

}
