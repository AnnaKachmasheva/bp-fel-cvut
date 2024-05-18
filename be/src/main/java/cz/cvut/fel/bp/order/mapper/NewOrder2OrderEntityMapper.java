package cz.cvut.fel.bp.order.mapper;

import cz.cvut.fel.bp.api.v1.model.NewOrder;
import cz.cvut.fel.bp.order.entity.OrderEntity;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import cz.cvut.fel.bp.product.mapper.Product2ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class NewOrder2OrderEntityMapper {

    private final Product2ProductEntityMapper product2ProductEntityMapper;

    public OrderEntity toOrderEntity(NewOrder newOrder,
                                     UUID creatorId) {

        if (newOrder == null) return null;

        OrderEntity orderEntity = new OrderEntity();
        List<ProductEntity> productEntityList = product2ProductEntityMapper.toProductEntityList(newOrder.getProductList());
        orderEntity.setProducts(productEntityList);
        orderEntity.setDescription(newOrder.getDescription());
        orderEntity.setCreatorId(creatorId);
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setUpdatedAt(LocalDateTime.now());

        return orderEntity;
    }

}
