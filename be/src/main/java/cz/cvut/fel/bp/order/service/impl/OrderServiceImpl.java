package cz.cvut.fel.bp.order.service.impl;

import cz.cvut.fel.bp.api.v1.model.NewOrder;
import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.common.utils.Utils;
import cz.cvut.fel.bp.exceptions.AlreadyCompleteException;
import cz.cvut.fel.bp.exceptions.NotFoundException;
import cz.cvut.fel.bp.exceptions.PermissionDeniedException;
import cz.cvut.fel.bp.order.entity.OrderEntity;
import cz.cvut.fel.bp.order.entity.StatusOrderEntity;
import cz.cvut.fel.bp.order.mapper.NewOrder2OrderEntityMapper;
import cz.cvut.fel.bp.order.mapper.OrderEntity2OrderMapper;
import cz.cvut.fel.bp.order.repository.OrderEntityRepository;
import cz.cvut.fel.bp.order.repository.StatusOrderEntityRepository;
import cz.cvut.fel.bp.order.service.OrderService;
import cz.cvut.fel.bp.product.entity.ProductEntity;
import cz.cvut.fel.bp.product.entity.StatusProductEntity;
import cz.cvut.fel.bp.product.mapper.Product2ProductEntityMapper;
import cz.cvut.fel.bp.product.repository.ProductEntityRepository;
import cz.cvut.fel.bp.product.repository.StatusProductEntityRepository;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.entity.UserRole;
import cz.cvut.fel.bp.user.model.CustomUserDetails;
import cz.cvut.fel.bp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author annak
 * @since 2024-04-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;

    private final OrderEntityRepository orderEntityRepository;
    private final StatusProductEntityRepository statusProductEntityRepository;
    private final ProductEntityRepository productEntityRepository;
    private final StatusOrderEntityRepository statusOrderEntityRepository;

    private final NewOrder2OrderEntityMapper newOrder2OrderEntityMapper;
    private final OrderEntity2OrderMapper orderEntity2OrderMapper;
    private final Product2ProductEntityMapper product2ProductEntityMapper;

    @Override
    public Order createOrder(NewOrder newOrder) {

        final User creator = userService.getCurrentUser();
        OrderEntity orderEntity = newOrder2OrderEntityMapper.toOrderEntity(newOrder, creator.getId());

//        String nameStatus = "PENDING_DELIVERY";
//        Optional<StatusProductEntity> statusProductOptional = statusProductEntityRepository.findByName(nameStatus);
//        if (statusProductOptional.isEmpty()) {
//            throw new NotFoundException("NO_FOUND_STATUS_PRODUCT" + nameStatus, "Not found product status");
//        }
//        StatusProductEntity statusProductEntity = statusProductOptional.get();
//
//        List<ProductEntity> productEntities = orderEntity.getProducts();
//        for (ProductEntity productEntity : productEntities) {
//            log.debug("Change status on PENDING_DELIVERY for product={}", productEntity.getId());
//            Optional<ProductEntity> updatedProductEntityOptional = productEntityRepository.findById(productEntity.getId());
//            ProductEntity updatedProductEntity = updatedProductEntityOptional.get();
//            updatedProductEntity.setStatus(statusProductEntity);
//            productEntityRepository.save(updatedProductEntity);
//        }

        log.debug("User={} created order={}.", creator, orderEntity);

        String newOrderStatus = "CREATED";
        Optional<StatusOrderEntity> statusOrderEntityOptional = statusOrderEntityRepository.findByName(newOrderStatus);
        if (statusOrderEntityOptional.isEmpty()) {
            throw new NotFoundException("NO_FOUND_STATUS_ORDER" + newOrderStatus, "Not found order status");
        }
        StatusOrderEntity statusOrderEntity = statusOrderEntityOptional.get();
        orderEntity.setStatus(statusOrderEntity);

        OrderEntity savedOrder = orderEntityRepository.save(orderEntity);
        UUID creatorId = savedOrder.getCreatorId();
        User savedCreator = userService.getUserById(creatorId);

        log.debug("Saved order={}", savedOrder);

        return orderEntity2OrderMapper.toOrder(savedOrder, savedCreator, null);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        OrderEntity orderEntity = getOrderEntityById(orderId);
        orderEntity.setUpdatedAt(LocalDateTime.now());

        log.debug("Delete order={}.", orderEntity);
        orderEntityRepository.delete(orderEntity);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        OrderEntity orderEntity = getOrderEntityById(orderId);
        log.debug("Find by id={} order={}", orderId, orderEntity);

        User creator = userService.getUserById(orderEntity.getCreatorId());
        User acceptor =  null;
        if (orderEntity.getAcceptorId() != null) {
             acceptor = userService.getUserById(orderEntity.getAcceptorId());
        }

        return orderEntity2OrderMapper.toOrder(orderEntity, creator, acceptor);
    }

    @Override
    public Order updateOrder(UUID orderId,
                             Order order) {

        OrderEntity orderEntity = getOrderEntityById(orderId);

        List<ProductEntity> productEntityList = product2ProductEntityMapper.toProductEntityList(order.getProduct());

        orderEntity.setDescription(order.getDescription());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setAcceptorId(order.getAcceptor().getId());
        orderEntity.setCreatorId(order.getCreator().getId());
        orderEntity.setProducts(productEntityList);

        log.debug("Before update order={} to={}.", orderEntity, order);
        OrderEntity savedOrderEntity = orderEntityRepository.save(orderEntity);

        log.debug("After update order={}", savedOrderEntity);

        return orderEntity2OrderMapper.toOrder(savedOrderEntity, order.getCreator(), order.getAcceptor());
    }

    @Override
    public Order updateOrderStatus(UUID orderId,
                                   Status status) {
        Optional<OrderEntity> orderEntityOptional = orderEntityRepository.findById(orderId);
        if (orderEntityOptional.isEmpty()) {
            log.error( "Not found order with id={}", orderId);
            throw new NotFoundException("NO_FOUND_ORDER", "Not found order with id=" + orderId);
        }

        Optional<StatusOrderEntity> statusOrderEntity = statusOrderEntityRepository.findByName(status.getName());
        if (statusOrderEntity.isEmpty()) {
            log.error( "Not found order status={}", status);
            throw new NotFoundException("NO_FOUND_ORDER_STATUS", "Not found order status=" + status);
        }

        OrderEntity orderEntity = orderEntityOptional.get();

        if (statusOrderEntity.get().getName().equals("PROCESSING")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            UserEntity currentUserEntity = principal.getUserEntity();
            orderEntity.setAcceptorId(currentUserEntity.getId());
        }

        // check order status
        if (orderEntity.getStatus().getName().equals("COMPLETED") ||
                orderEntity.getStatus().getName().equals("CANCELED")) {
            log.error("Order with id={}already completed", orderId);
            throw new AlreadyCompleteException("ALREADY_COMPLETED_ORDER", "Order with id=" + orderId + "already completed");
        }

        // check user permission
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        UserEntity currentUserEntity = principal.getUserEntity();
        UserRole role = currentUserEntity.getRole();


        if ((role == UserRole.ROLE_USER) && (orderEntity.getStatus().getName().equals("BACKORDERED"))) {
            log.error("User cannot change status order with id={}", orderId);
            throw new PermissionDeniedException("CANNOT_CHANGE_STATUS", "User cannot change status order with id=" + orderId);
        }

        if ((role == UserRole.ROLE_ADMIN) && !(orderEntity.getStatus().getName().equals("BACKORDERED"))) {
            log.error("User cannot change status order with id={}", orderId);
            throw new PermissionDeniedException("CANNOT_CHANGE_STATUS", "User cannot change status order with id=" + orderId);
        }

        orderEntity.setStatus(statusOrderEntity.get());

        OrderEntity updatedOrderEntity = orderEntityRepository.save(orderEntity);

        User creator = userService.getUserById(orderEntity.getCreatorId());
        User acceptor = null;
        if (orderEntity.getAcceptorId() != null) {
            acceptor = userService.getUserById(orderEntity.getAcceptorId());
        }

        List<ProductEntity> productEntities = orderEntity.getProducts();
        for (ProductEntity productEntity : productEntities) {
            String statusName = productEntity.getStatus().getName();
            if (statusName.equals("IN_STOCK")) {
                Optional<StatusProductEntity> statusProduct = statusProductEntityRepository.findByName("PENDING_DELIVERY");
                if (statusProduct.isEmpty()) {
                    log.error("Not found new product status={}", orderId);
                    throw new NotFoundException("NO_FOUND_NEW_PRODUCT_STATUS", "Not found new product status");
                }
                productEntity.setStatus(statusProduct.get());
            }

            if (statusName.equals("PENDING_DELIVERY") && orderEntity.getStatus().getName().equals("COMPLETED")) {
                Optional<StatusProductEntity> statusOrder = statusProductEntityRepository.findByName("DELIVERED");
                if (statusOrder.isEmpty()) {
                    log.error("Not found new product status={}", "DELIVERED");
                    throw new NotFoundException("NO_FOUND_NEW_PRODUCT_STATUS", "Not found new product status");
                }
                productEntity.setStatus(statusOrder.get());
            }

            if (statusName.equals("DELIVERED") || orderEntity.getStatus().getName().equals("CANCELED")) {
                Optional<StatusProductEntity> statusOrder = statusProductEntityRepository.findByName("IN_STOCK");
                if (statusOrder.isEmpty()) {
                    log.error("Not found new product status={}", "IN_STOCK");
                    throw new NotFoundException("NO_FOUND_NEW_PRODUCT_STATUS", "Not found new product status");
                }
                productEntity.setStatus(statusOrder.get());
            }

            productEntityRepository.save(productEntity);
        }

        return orderEntity2OrderMapper.toOrder(updatedOrderEntity, creator, acceptor);
    }

    /**
     * Retrieves an order entity by its ID.
     *
     * @param orderId The ID of the order entity to retrieve.
     * @return The order entity with the specified ID.
     * @throws NotFoundException If no order entity with the specified ID is found.
     */
    private OrderEntity getOrderEntityById(UUID orderId) {
        Utils.nonNull(orderId);

        Optional<OrderEntity> orderEntityOptional = orderEntityRepository.findById(orderId);
        if (orderEntityOptional.isEmpty()) {
            throw new NotFoundException("ORDER_NOT_FOUND", "Order with id={" + orderId + "} not found.");
        }
        OrderEntity orderEntity = orderEntityOptional.get();
        log.info("Found by id={} productEntity={}.", orderId, orderEntity);

        return orderEntity;
    }
}
