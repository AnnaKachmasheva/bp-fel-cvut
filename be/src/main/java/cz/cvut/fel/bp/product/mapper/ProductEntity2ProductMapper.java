package cz.cvut.fel.bp.product.mapper;

import cz.cvut.fel.bp.api.v1.model.*;
import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class ProductEntity2ProductMapper {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Product toProduct(ProductEntity productEntity) {
        if (productEntity == null) return null;

        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setIsDeleted(productEntity.isDeleted());

        var uptdatedAt = productEntity.getUpdatedAt();
        if (uptdatedAt != null) {
            product.setUpdatedAt(uptdatedAt.format(formatter));
        }

        var createdAt = productEntity.getCreatedAt();
        if (createdAt != null) {
            product.setCreatedAt(createdAt.format(formatter));
        }

        Category category = new Category();
        CategoryEntity categoryEntity = productEntity.getCategory();
        if (categoryEntity != null) {
            category.setName(productEntity.getCategory().getName());
        }
        product.setCategory(category);

        StatusProductEntity statusProductEntity = productEntity.getStatus();
        if (statusProductEntity != null) {
            Status productStatus = new Status();
            productStatus.setName(statusProductEntity.getName());
            product.setStatus(productStatus);
        }

        return product;
    }

    public List<Product> toProductList(List<ProductEntity> productEntities) {
        List<Product> products = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            Product product = toProduct(productEntity);
            products.add(product);
        }

        return products;
    }

}
