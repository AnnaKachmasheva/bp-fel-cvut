package cz.cvut.fel.bp.order.service;

import cz.cvut.fel.bp.api.v1.model.NewOrder;
import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.Status;

import java.util.UUID;

/**
 * This interface defines methods for managing individual orders.
 * <p>
 * An order represents a task or request made by a user.
 * </p>
 *
 * @author annak
 * @since 2024-04-10
 */
public interface OrderService {

    /**
     * Creates a new order based on the provided data.
     *
     * @param newOrder The data for the new order.
     * @return The created order.
     */
    Order createOrder(NewOrder newOrder);

    /**
     * Deletes an order with the specified ID.
     *
     * @param orderId The ID of the order to delete.
     */
    void deleteOrder(UUID orderId);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    Order getOrderById(UUID orderId);

    /**
     * Updates an existing order with new data.
     *
     * @param orderId The ID of the order to update.
     * @param order   The updated order data.
     * @return The updated order.
     */
    Order updateOrder(UUID orderId, Order order);

    Order updateOrderStatus(UUID orderId, Status status);

}
