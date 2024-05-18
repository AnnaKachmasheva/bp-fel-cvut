package cz.cvut.fel.bp.product.repository;

import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusProductEntityRepository extends JpaRepository<StatusProductEntity, Long> {

    Optional<StatusProductEntity> findByName(String name);

}