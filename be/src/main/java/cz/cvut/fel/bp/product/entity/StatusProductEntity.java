package cz.cvut.fel.bp.product.entity;

import cz.cvut.fel.bp.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_status")
public class StatusProductEntity extends BaseEntity {

    private String name;

}