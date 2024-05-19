package cz.cvut.fel.bp.product.service.impl;

import cz.cvut.fel.bp.api.v1.model.*;
import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.category.repository.CategoryEntityRepository;
import cz.cvut.fel.bp.exceptions.NotFoundException;
import cz.cvut.fel.bp.order.entity.OrderEntity;
import cz.cvut.fel.bp.order.entity.StatusOrderEntity;
import cz.cvut.fel.bp.order.repository.OrderEntityRepository;
import cz.cvut.fel.bp.order.repository.StatusOrderEntityRepository;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import cz.cvut.fel.bp.product.mapper.NewProduct2ProductEntityMapper;
import cz.cvut.fel.bp.product.mapper.ProductEntity2ProductMapper;
import cz.cvut.fel.bp.product.mapper.StatusProduct2ProductStatusMapper;
import cz.cvut.fel.bp.product.repository.ProductEntityRepository;
import cz.cvut.fel.bp.product.repository.StatusProductEntityRepository;
import cz.cvut.fel.bp.product.service.ProductsService;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {


    private final ProductEntityRepository productEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final StatusProductEntityRepository statusProductEntityRepository;
    private final OrderEntityRepository orderEntityRepository;
    private final StatusOrderEntityRepository statusOrderEntityRepository;

    private final ProductEntity2ProductMapper productEntity2ProductMapper;
    private final NewProduct2ProductEntityMapper newProduct2ProductEntityMapper;
    private final StatusProduct2ProductStatusMapper statusProduct2ProductStatusMapper;


    @Override
    public Optional<UUID> createProduct(NewProduct newProduct) {
        ProductEntity productEntity = newProduct2ProductEntityMapper.toProductEntity(newProduct);
        if (productEntity == null) {
            log.debug("Cannot create new product because request is null");
            return Optional.empty();
        }

        Category category = newProduct.getCategory();
        Optional<CategoryEntity> categoryEntity = categoryEntityRepository.findCategoryEntitiesByName(category.getName());
        categoryEntity.ifPresent(productEntity::setCategory);
        log.debug("Category={}", categoryEntity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        UserEntity currentUserEntity = principal.getUserEntity();
        log.debug("Current user={}.", currentUserEntity);
        productEntity.setCreatorId(currentUserEntity.getId());

        String statusName = newProduct.getStatus().getName();
        Optional<StatusProductEntity> statusProduct = statusProductEntityRepository.findByName(statusName);
        statusProduct.ifPresent(productEntity::setStatus);
        log.debug("Status={}", statusProduct);

        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());

        // todo save images

        log.info("Save new productEntity={}.", productEntity);

        ProductEntity savedProductEntity = productEntityRepository.save(productEntity);
        log.info("Saved product={}.", savedProductEntity);
        return Optional.of(savedProductEntity.getId());
    }

    @Override
    public List<Status> getProductStatuses() {
        log.debug("in getProductStatuses");

        List<StatusProductEntity> statusProductEntities = statusProductEntityRepository.findAll();
        log.debug("Founded product statuses={}", statusProductEntities);
        return statusProduct2ProductStatusMapper.toProductStatuseList(statusProductEntities);
    }

    @Override
    public ProductPage getProducts(List<Category> categories,
                                   Pageable pageable) {

        log.debug("Get all products");

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (Category category : categories) {
            Optional<CategoryEntity> categoryEntityOptional = categoryEntityRepository.findCategoryEntitiesByName(category.getName());
            categoryEntityOptional.ifPresent(categoryEntities::add);
        }

        Page<ProductEntity> productEntityPage = productEntityRepository.findAllByCategory(categoryEntities, pageable);

        log.info("Found product page={} .", productEntityPage);

        List<Product> products = productEntity2ProductMapper.toProductList(productEntityPage.getContent());

        ProductPage productPage = new ProductPage();
        productPage.setContent(products);
        productPage.setTotalPages(productEntityPage.getTotalPages());
        productPage.setTotalElements(productEntityPage.getTotalElements());
        productPage.setSize(productEntityPage.getSize());
        productPage.setPageable(pageable);
        productPage.setNumberOfElements(products.size());
        productPage.setNumber(pageable.getPageNumber());
        productPage.setEmpty(productEntityPage.isEmpty());
        productPage.setFirst(pageable.first().isPaged());

        log.info("Found products page={}.", productPage);

        return productPage;
    }

    @Override
    public List<Product> getProducts(String status) {
        Optional<StatusProductEntity> statusProductOptional = statusProductEntityRepository.findByName(status);
        if (statusProductOptional.isEmpty()) {
            throw new NotFoundException("STATUS_NOT_FOUND", "Status" + status + "not found");
        }

        StatusProductEntity statusProduct = statusProductOptional.get();
        log.info("Status product={}", statusProduct); // todo debug

        List<ProductEntity> productEntities = productEntityRepository.findAllByStatusAndIsDeletedIsFalse(statusProduct);
        log.info("Found {} products with status={}", productEntities.size(), status);  // todo debug

        // must be products with status IN_STOCK and not in order with status CREATED
        Optional<StatusOrderEntity> statusOrderEntityOptional = statusOrderEntityRepository.findByName("CREATED");
        if (statusOrderEntityOptional.isEmpty()) {
            log.error("Order status=CREATED not found");
            throw new NotFoundException("STATUS_NOT_FOUND", "Status=CREATED not found");
        }

        List<OrderEntity> orderEntities = orderEntityRepository.findAllByStatus(List.of(statusOrderEntityOptional.get()));
        log.info("Found {} orders with status={}", orderEntities.size(), statusOrderEntityOptional.get());  // todo debug

        List<ProductEntity> resultProductList = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            if (isAlreadyExist(orderEntities, productEntity)) {
                log.debug("Product already reserved.");
            } else {
                resultProductList.add(productEntity);
            }
        }

        return productEntity2ProductMapper.toProductList(resultProductList);
    }

    private boolean isAlreadyExist(List<OrderEntity> orderEntities,
                                   ProductEntity productEntity) {

        for (OrderEntity orderEntity : orderEntities) {
            List<ProductEntity> productsInOrder = orderEntity.getProducts();
            for (ProductEntity productInOrder : productsInOrder) {
                if (productEntity.getId().equals(productInOrder.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

}
