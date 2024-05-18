package cz.cvut.fel.bp.common.config.db;

import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.category.repository.CategoryEntityRepository;
import cz.cvut.fel.bp.order.entity.StatusOrderEntity;
import cz.cvut.fel.bp.order.repository.StatusOrderEntityRepository;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import cz.cvut.fel.bp.product.repository.StatusProductEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author annak
 * @since 2024-05-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final StatusProductEntityRepository statusProductEntityRepository;
    private final StatusOrderEntityRepository statusOrderEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;


    @Override
    public void run(String... args) throws Exception {
        // init status product
        createStatusProduct();

        // init status order
        createStatusOrder();

        // create base categories
        createBaseCategories();
    }

    private void createBaseCategories() {
        String[] categoryNames = {"Electronics", "Books", "Clothing", "Home & Kitchen", "Sports"};

        for (String categoryName : categoryNames) {
            Optional<CategoryEntity> existingCategory = categoryEntityRepository.findCategoryEntitiesByName(categoryName);

            if (existingCategory.isEmpty()) {
                CategoryEntity category = new CategoryEntity();
                category.setName(categoryName);
                category.setProducts(new ArrayList<>());
                category.setUpdatedAt(LocalDateTime.now());
                category.setCreatedAt(LocalDateTime.now());

                log.debug("Saving base category={}", category);
                categoryEntityRepository.save(category);
            } else {
                log.debug("Category={} already exists", categoryName);
            }
        }
    }

    private void createStatusOrder() {
        String[] statusNames = {"CREATED", "PROCESSING", "COMPLETED", "CANCELED", "BACKORDERED"};

        for (String statusName : statusNames) {
            Optional<StatusOrderEntity> existingStatus = statusOrderEntityRepository.findByName(statusName);

            if (existingStatus.isEmpty()) {
                StatusOrderEntity statusOrder = new StatusOrderEntity();
                statusOrder.setName(statusName);

                log.debug("Saving status order={}", statusOrder);
                statusOrderEntityRepository.save(statusOrder);
            } else {
                log.debug("Status order={} already exists", existingStatus);
            }
        }
    }

    private void createStatusProduct() {
        String[] statusNames = {"IN_STOCK", "PENDING_DELIVERY", "DELIVERED"};

        for (String statusName : statusNames) {
            Optional<StatusProductEntity> existingStatus = statusProductEntityRepository.findByName(statusName);

            if (existingStatus.isEmpty()) {
                StatusProductEntity status = new StatusProductEntity();
                status.setName(statusName);

                log.debug("Saving status order={}", status);
                statusProductEntityRepository.save(status);
            } else {
                log.debug("Status order={} already exists", existingStatus);
            }
        }
    }

}
