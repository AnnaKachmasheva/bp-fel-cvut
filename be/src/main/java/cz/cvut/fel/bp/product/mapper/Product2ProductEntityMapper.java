package cz.cvut.fel.bp.product.mapper;

import cz.cvut.fel.bp.api.v1.model.Product;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class Product2ProductEntityMapper {


    public ProductEntity toProductEntity(Product product) {
        if (product == null) return null;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setDeleted(product.getIsDeleted());

        return productEntity;
    }

    public List<ProductEntity> toProductEntityList(List<Product> products) {
        if (products == null) return null;

        List<ProductEntity> productEntities = new ArrayList<>();

        for (Product product : products) {
            ProductEntity productEntity = toProductEntity(product);
            productEntities.add(productEntity);
        }

        return productEntities;
    }

}
