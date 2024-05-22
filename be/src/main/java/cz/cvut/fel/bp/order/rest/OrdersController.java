package cz.cvut.fel.bp.order.rest;

import cz.cvut.fel.bp.api.v1.model.OrderPage;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.rest.OrdersApi;
import cz.cvut.fel.bp.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class OrdersController implements OrdersApi {

    private final OrdersService ordersService;

    @Override
    public ResponseEntity<OrderPage> getOrders(List<Status> statuses,
                                               Pageable pageable) {
        log.info("Get all orders. Pageable={}. Statuses={}", pageable, statuses);

        return ResponseEntity.ok(ordersService.getAllOrders(pageable, statuses));
    }


}
