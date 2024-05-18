package cz.cvut.fel.bp.product.entity;

import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.common.entity.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-16
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "PRODUCT")
public class ProductEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    private StatusProductEntity status;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(nullable = false)
    private UUID creatorId;

    @Column(name = "is_deleted",
            nullable = false,
            columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Timestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
