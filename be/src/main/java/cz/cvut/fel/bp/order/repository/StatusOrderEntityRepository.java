package cz.cvut.fel.bp.order.repository;

import cz.cvut.fel.bp.order.entity.StatusOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusOrderEntityRepository extends JpaRepository<StatusOrderEntity, Long> {

    Optional<StatusOrderEntity> findByName(String name);

}