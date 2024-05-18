package cz.cvut.fel.bp.order.rest;

import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.rest.OrderStatusesApi;
import cz.cvut.fel.bp.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class OrderStatusController implements OrderStatusesApi {

    private final OrdersService productsService;

    @Override
    public ResponseEntity<List<Status>> getOrderStatuses() {
        log.info("Get all order statuses.");

        List<Status> statuses = productsService.getOrderStatuses();
        return ResponseEntity.ok(statuses);
    }
}
