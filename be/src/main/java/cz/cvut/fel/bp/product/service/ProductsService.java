package cz.cvut.fel.bp.product.service;

import cz.cvut.fel.bp.api.v1.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author annak
 * @since 2024-03-29
 */
public interface ProductsService {


    /**
     * @param pageable
     * @return
     */
    ProductPage getProducts(List<Category> categories, Pageable pageable);

    List<Product> getProducts(String status);


    void createProduct(NewProduct newProduct);

    List<Status> getProductStatuses();
}
