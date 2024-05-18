package cz.cvut.fel.bp.order.mapper;

import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.Product;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.order.entity.OrderEntity;
import cz.cvut.fel.bp.product.mapper.ProductEntity2ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author annak
 * @since 2024-03-14
 */
@Component
@RequiredArgsConstructor
public class OrderEntity2OrderMapper {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private final ProductEntity2ProductMapper productEntity2ProductMapper;

    public Order toOrder(OrderEntity orderEntity,
                         User creator,
                         User acceptor) {
        if (orderEntity == null) return null;

        List<Product> products = productEntity2ProductMapper.toProductList(orderEntity.getProducts());

        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setDescription(orderEntity.getDescription());
        order.setProduct(products);
        order.setCreator(creator);
        order.setAcceptor(acceptor);

        Status status = new Status();
        status.setName(orderEntity.getStatus().getName());
        order.setStatus(status);

        var updatedAt = orderEntity.getUpdatedAt();
        if (updatedAt != null) {
            order.setCreatedAt(updatedAt.format(formatter));
        }

        var createdAt = orderEntity.getCreatedAt();
        if (createdAt != null) {
            order.setUpdatedAt(createdAt.format(formatter));
        }

        return order;
    }

}
