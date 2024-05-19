package cz.cvut.fel.bp.product.rest;

import cz.cvut.fel.bp.api.v1.model.Category;
import cz.cvut.fel.bp.api.v1.model.NewProduct;
import cz.cvut.fel.bp.api.v1.model.Product;
import cz.cvut.fel.bp.api.v1.model.ProductPage;
import cz.cvut.fel.bp.api.v1.rest.ProductsApi;
import cz.cvut.fel.bp.product.component.ProductComponent;
import cz.cvut.fel.bp.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ProductsController implements ProductsApi {

    private final ProductsService productsService;
    private final ProductComponent productComponent;

    @Override
    public ResponseEntity<Void> createProduct(NewProduct newProduct, MultipartFile imageFile) {
        log.info("Create new product. New product={}", newProduct);

        productComponent.createProduct(newProduct, imageFile);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProductPage> getProducts(List<Category> categories,
                                                   Pageable pageable) {
        log.info("Get all products. {}. Categories={}", pageable, categories);

        return ResponseEntity.ok(productComponent.getProductList(categories, pageable));
    }


    @Override
    public ResponseEntity<List<Product>> getProductsByStatus(String status) {
        log.info("Get products by status={}", status);

        return ResponseEntity.ok(productComponent.getProductListByStatus(status));
    }
}
