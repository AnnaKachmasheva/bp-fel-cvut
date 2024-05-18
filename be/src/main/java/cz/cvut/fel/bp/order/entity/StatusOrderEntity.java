package cz.cvut.fel.bp.order.entity;

import cz.cvut.fel.bp.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class StatusOrderEntity extends BaseEntity {
//    CREATED,
//    PROCESSING,
//    COMPLETED
//    CANCELED
//    BACKORDERED


    private String name;

}