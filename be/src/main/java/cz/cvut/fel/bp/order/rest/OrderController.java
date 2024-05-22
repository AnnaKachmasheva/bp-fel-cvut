package cz.cvut.fel.bp.order.rest;

import cz.cvut.fel.bp.api.v1.model.NewOrder;
import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.rest.OrderApi;
import cz.cvut.fel.bp.order.component.OrderComponent;
import cz.cvut.fel.bp.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController implements OrderApi {

    private final OrderComponent orderComponent;
    private final OrderService orderService;


    @Override
    public ResponseEntity<Order> createOrder(NewOrder newOrder) {
        log.info("Create order={}.", newOrder);

        Order createdOrder = orderService.createOrder(newOrder);

        return ResponseEntity.ok(createdOrder);
    }


    @Override
    public ResponseEntity<Void> deleteOrderBy(UUID orderId) {
        log.info("Delete order. OrderId={}.", orderId);

        orderService.deleteOrder(orderId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Order> getOrderById(UUID orderId) {
        log.info("Get order. OrderId={}.", orderId);

        Order order = orderComponent.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<Order> updateOrder(UUID orderId,
                                             Order order) {
        final Order updatedOrder = orderService.updateOrder(orderId, order);

        log.info("Updated order={}.", order);

        return ResponseEntity.ok(updatedOrder);
    }

    @Override
    public ResponseEntity<Order> updateOrderStatus(UUID orderId,
                                                   Status status) {
        log.info("Updated order={}. new status={}", orderId, status);
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

}
