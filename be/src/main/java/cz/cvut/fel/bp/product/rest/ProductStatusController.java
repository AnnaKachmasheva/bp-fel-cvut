package cz.cvut.fel.bp.product.rest;

import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.rest.ProductStatusesApi;
import cz.cvut.fel.bp.product.service.ProductsService;
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
public class ProductStatusController implements ProductStatusesApi {

    private final ProductsService productsService;


    @Override
    public ResponseEntity<List<Status>> getProductStatuses() {
        log.info("Get all statuses products.");

        List<Status> statuses = productsService.getProductStatuses();
        return ResponseEntity.ok(statuses);
    }

}
