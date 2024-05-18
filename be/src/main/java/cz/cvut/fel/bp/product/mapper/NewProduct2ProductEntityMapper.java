package cz.cvut.fel.bp.product.mapper;

import cz.cvut.fel.bp.api.v1.model.NewProduct;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class NewProduct2ProductEntityMapper {

    public ProductEntity toProductEntity(NewProduct newProduct) {
        if (newProduct == null) return null;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(newProduct.getName());
        productEntity.setDescription(newProduct.getDescription());
        productEntity.setDeleted(false);
        productEntity.setUpdatedAt(LocalDateTime.now());
        productEntity.setCreatedAt(LocalDateTime.now());

//TODO

        return productEntity;
    }

}
