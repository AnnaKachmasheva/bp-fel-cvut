package cz.cvut.fel.bp.product.repository;

import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {


    List<ProductEntity> findAllByStatusAndIsDeletedIsFalse(StatusProductEntity status);

    Optional<ProductEntity> findById(UUID id);

    @Query("""
            SELECT t FROM ProductEntity t WHERE t.category IN (:categories)
           """)
    Page<ProductEntity> findAllByCategory(@Param("categories") List<CategoryEntity> categories, Pageable pageable);

}