package cz.cvut.fel.bp.category.entity;

import cz.cvut.fel.bp.common.entity.BaseEntity;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CATEGORIES")
public class CategoryEntity extends BaseEntity {


    private String name;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "category",
            cascade = CascadeType.ALL)
    private List<ProductEntity> products;

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


    @Override
    public String toString() {
        return "CategoryEntity{" +
                "name='" + name + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
