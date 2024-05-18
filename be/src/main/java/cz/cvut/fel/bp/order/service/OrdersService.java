package cz.cvut.fel.bp.order.service;

import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.OrderPage;
import cz.cvut.fel.bp.api.v1.model.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * This interface defines methods for managing orders.
 * <p>
 * An order represents a task or request made by a user.
 * </p>
 *
 * @author annak
 * @since 2024-04-11
 */
public interface OrdersService {

    /**
     * Retrieves a list of all orders with pagination support.
     *
     * @param pageable Pagination information.
     * @param statuses
     * @return A list of orders.
     */
    OrderPage getAllOrders(Pageable pageable, List<Status> statuses);

    /**
     * Retrieves a list of all orders created by a specific user.
     *
     * @param userId The ID of the user who created the orders.
     * @return A list of orders created by the specified user.
     */
    List<Order> getAllCreatedOrders(UUID userId);

    /**
     * Retrieves a list of all orders accepted by a specific user.
     *
     * @param userId The ID of the user who accepted the orders.
     * @return A list of orders accepted by the specified user.
     */
    List<Order> getAllAcceptedOrders(UUID userId);

    List<Status> getOrderStatuses();
}
