package cz.cvut.fel.bp.product.service.impl;

import cz.cvut.fel.bp.api.v1.model.Product;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.category.repository.CategoryEntityRepository;
import cz.cvut.fel.bp.common.utils.Utils;
import cz.cvut.fel.bp.exceptions.NotFoundException;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import cz.cvut.fel.bp.product.mapper.ProductEntity2ProductMapper;
import cz.cvut.fel.bp.product.repository.ProductEntityRepository;
import cz.cvut.fel.bp.product.repository.StatusProductEntityRepository;
import cz.cvut.fel.bp.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductEntityRepository productEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final StatusProductEntityRepository statusProductEntityRepository;

    private final ProductEntity2ProductMapper productEntity2ProductMapper;


    @Override
    @Transactional
    public Product getProduct(UUID productId) {
        Utils.nonNull(productId);

        final ProductEntity productEntity = getProductEntityById(productId);

        log.info("Found by id={} productEntity={}.", productId, productEntity);

        return productEntity2ProductMapper.toProduct(productEntity);
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, Product product) {
        Utils.nonNull(productId);

        final ProductEntity productEntity = getProductEntityById(productId);

        log.info("Before update product={}.", productEntity);

        Optional<CategoryEntity> categoryEntity =  categoryEntityRepository.findCategoryEntitiesByName(product.getCategory().getName());
        categoryEntity.ifPresent(productEntity::setCategory);

        Optional<StatusProductEntity> statusProduct =  statusProductEntityRepository.findByName(product.getStatus().getName());
        statusProduct.ifPresent(productEntity::setStatus);

        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setDeleted(product.getIsDeleted());
        productEntity.setUpdatedAt(LocalDateTime.now());

        // save to DB
        final ProductEntity savedProductEntity = productEntityRepository.save(productEntity);

        log.info("After update product={}.", savedProductEntity);

        return productEntity2ProductMapper.toProduct(savedProductEntity);
    }

    @Override
    @Transactional
    public Status updateProductStatus(UUID productId, Status productStatus) {
        Utils.nonNull(productId);

        final ProductEntity productEntity = getProductEntityById(productId);

        log.info("Before update status product={}. new status={}", productEntity, productStatus);

//        productEntity.setStatus(StatusProduct.valueOf(productStatus.toString()));

        // save to DB
        final ProductEntity savedProductEntity = productEntityRepository.save(productEntity);

        log.info("After update status product={}.", savedProductEntity);

        final Status newProductStatus = new Status();
//        newProductStatus.setStatus(Status.valueOf(savedProductEntity.getStatus().toString()));
        return newProductStatus;
    }

    @Override
    public void deleteProduct(UUID id) {
        Utils.nonNull(id);

        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(id);

        if (productEntityOptional.isEmpty()) {
            throw new NotFoundException("PRODUCT_NOT_FOUND", "Product with id" + id + "not found.");
        }

        ProductEntity productEntity = productEntityOptional.get();

        // product deleted ?
        if (productEntity.isDeleted()){
            log.debug("Product with id={} is already deleted.", id);
            return;
        }

        log.debug("Before deletion product={}.", productEntity);

        productEntity.setDeleted(true); // delete user
        productEntity.setUpdatedAt(LocalDateTime.now());
        ProductEntity deletedProductEntity = productEntityRepository.save(productEntity);
        log.debug("After deletion product ={}..", deletedProductEntity);
    }

    private ProductEntity getProductEntityById(UUID id) {
        Utils.nonNull(id);

        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(id);
        if (productEntityOptional.isEmpty())
            throw new NotFoundException("PRODUCT_NOT_FOUND", "Product with id={" + id + "} not found.");

        return productEntityOptional.get();
    }

}
