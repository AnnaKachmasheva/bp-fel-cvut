package cz.cvut.fel.bp.product.service;

import cz.cvut.fel.bp.api.v1.model.Product;
import cz.cvut.fel.bp.api.v1.model.Status;

import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-29
 */
public interface ProductService {

    /**
     *
     * @param productId
     * @return
     */
    Product getProduct(UUID productId);

    /**
     *
     * @param productId
     * @param product
     * @return
     */
    Product updateProduct(UUID productId, Product product);

    /**
     *
     * @param productId
     * @param productStatus
     * @return
     */
    Status updateProductStatus(UUID productId, Status productStatus);

    void deleteProduct(UUID id);
}
