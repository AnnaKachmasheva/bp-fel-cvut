package cz.cvut.fel.bp.user.rest;

import cz.cvut.fel.bp.api.v1.model.ChangePassword;
import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.api.v1.rest.UserApi;
import cz.cvut.fel.bp.order.service.OrdersService;
import cz.cvut.fel.bp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "https://master.d3f81l92tk91tc.amplifyapp.com", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController implements UserApi {

    private final UserService userService;
    private final OrdersService ordersService;

    @Override
    public ResponseEntity<Void> deleteUserBy(UUID userId) {
        log.info("Delete user. UserId={}.", userId);

        userService.deleteUser(userId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<User> getUserById(UUID userId) {
        log.info("Get user. UserId={}.", userId);

        final User user = userService.getUserById(userId);

        log.info("Found user={} by Id={}.", user, userId);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> updateUser(UUID userId,
                                           User user) {

        final User updatedUser = userService.updateUser(userId, user);

        log.info("Updated user={}.", updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<List<Order>> getAcceptedOrdersByUserId(UUID userId) {

        log.info("Get all accepted orders for user with id={}.", userId);

        return ResponseEntity.ok(ordersService.getAllAcceptedOrders(userId));
    }

    @Override
    public ResponseEntity<List<Order>> getCreatedOrdersByUserId(UUID userId) {
        log.info("Get all created orders for user with id={}.", userId);

        return ResponseEntity.ok(ordersService.getAllCreatedOrders(userId));
    }

    @Override
    public ResponseEntity<Void> changePassword(UUID userId,
                                               ChangePassword changePassword) {

        log.info("Change password={} for user with id={}.", changePassword, userId);

        userService.changePassword(userId, changePassword);

        return ResponseEntity.ok().build();
    }

}
