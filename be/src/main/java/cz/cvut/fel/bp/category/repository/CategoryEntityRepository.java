package cz.cvut.fel.bp.category.repository;

import cz.cvut.fel.bp.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findCategoryEntitiesByName(String name);

}