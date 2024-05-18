package cz.cvut.fel.bp.order.repository;

import cz.cvut.fel.bp.order.entity.OrderEntity;
import cz.cvut.fel.bp.order.entity.StatusOrderEntity;
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
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByCreatorId(UUID creatorId);

    List<OrderEntity> findAllByAcceptorId(UUID creatorId);

    Optional<OrderEntity> findById(UUID orderId);

    @Query("""
            SELECT t FROM OrderEntity t WHERE t.status IN (:statuses)
           """)
    Page<OrderEntity> findAllByStatus(@Param("statuses") List<StatusOrderEntity> statuses, Pageable pageable);


    @Query("""
            SELECT t FROM OrderEntity t WHERE t.status IN (:statuses)
           """)
    List<OrderEntity> findAllByStatus(@Param("statuses") List<StatusOrderEntity> statuses);

}