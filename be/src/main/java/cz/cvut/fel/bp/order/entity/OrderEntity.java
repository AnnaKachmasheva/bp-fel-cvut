package cz.cvut.fel.bp.order.entity;

import cz.cvut.fel.bp.common.entity.BaseEntity;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "ORDERS")
public class OrderEntity extends BaseEntity {


    private String description;

    private UUID creatorId;

    private UUID acceptorId;

    @ManyToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    private List<ProductEntity> products;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusOrderEntity status;

    @Timestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
