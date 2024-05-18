package cz.cvut.fel.bp.product.rest;

import cz.cvut.fel.bp.api.v1.model.Product;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.rest.ProductApi;
import cz.cvut.fel.bp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ProductController implements ProductApi {

    private final ProductService productService;


    @Override
    public ResponseEntity<Void> deleteProductBy(UUID productId) {
        log.info("Delete product. Id={}.", productId);

        productService.deleteProduct(productId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Product> getProductById(UUID productId) {
        log.info("Get product. ProductId={}.", productId);

        Product product = productService.getProduct(productId);

        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<Product> updateProduct(UUID productId, Product product) {
        log.info("Update product={} with id={}.", product, productId);

        final Product updatedProduct = productService.updateProduct(productId, product);

        log.info("Updated product={}.", product);

        return ResponseEntity.ok(updatedProduct);
    }

    @Override
    public ResponseEntity<Status> updateProductStatus(UUID productId, Status status) {
        log.info("Update product with id={} status={}.", productId, status);

        final Status updatedProductStatus = productService.updateProductStatus(productId, status);

        return ResponseEntity.ok(updatedProductStatus);
    }

}
