package cz.cvut.fel.bp.product.mapper;

import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
public class StatusProduct2ProductStatusMapper {

    public Status toProductStatus(StatusProductEntity statusProductEntity) {
        if (statusProductEntity == null) return null;

        Status productStatus = new Status();
        productStatus.setName(statusProductEntity.getName());

        return productStatus;
    }

    public List<Status> toProductStatuseList(List<StatusProductEntity> statusProductEntities) {
        List<Status> productStatuses = new ArrayList<>();

        for (StatusProductEntity statusProductEntity : statusProductEntities) {
            Status productStatus = toProductStatus(statusProductEntity);
            productStatuses.add(productStatus);
        }

        return productStatuses;
    }

}
