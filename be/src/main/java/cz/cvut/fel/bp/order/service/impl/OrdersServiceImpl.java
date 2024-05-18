package cz.cvut.fel.bp.order.service.impl;

import cz.cvut.fel.bp.api.v1.model.Order;
import cz.cvut.fel.bp.api.v1.model.OrderPage;
import cz.cvut.fel.bp.api.v1.model.Status;
import cz.cvut.fel.bp.api.v1.model.User;
import cz.cvut.fel.bp.order.entity.OrderEntity;
import cz.cvut.fel.bp.order.entity.StatusOrderEntity;
import cz.cvut.fel.bp.order.mapper.OrderEntity2OrderMapper;
import cz.cvut.fel.bp.order.repository.OrderEntityRepository;
import cz.cvut.fel.bp.order.repository.StatusOrderEntityRepository;
import cz.cvut.fel.bp.order.service.OrdersService;
import cz.cvut.fel.bp.user.entity.UserEntity;
import cz.cvut.fel.bp.user.model.CustomUserDetails;
import cz.cvut.fel.bp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OrdersServiceImpl implements OrdersService {

    private final UserService userService;

    private final OrderEntityRepository orderEntityRepository;
    private final StatusOrderEntityRepository statusOrderEntityRepository;

    private final OrderEntity2OrderMapper orderEntity2OrderMapper;


    @Override
    public OrderPage getAllOrders(Pageable pageable,
                                  List<Status> statuses) {
        log.debug("Get all orders.");

        List<StatusOrderEntity> statusOrderEntities = new ArrayList<>();
        for (Status status : statuses) {
            Optional<StatusOrderEntity> statusOrderOptional = statusOrderEntityRepository.findByName(status.getName());
            statusOrderOptional.ifPresent(statusOrderEntities::add);
        }

        Page<OrderEntity> orderEntitiesPage = orderEntityRepository.findAllByStatus(statusOrderEntities, pageable);

        List<OrderEntity> orderEntities = orderEntitiesPage.getContent();

        List<Order> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            User creator = userService.getUserById(orderEntity.getCreatorId());
            User acceptor = null;
            if (orderEntity.getAcceptorId() != null) {
                acceptor = userService.getUserById(orderEntity.getAcceptorId());
            }

            Order order = orderEntity2OrderMapper.toOrder(orderEntity, creator, acceptor);
            orders.add(order);
        }

        log.info("Orders size={}", orders.size());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        UserEntity currentUserEntity = principal.getUserEntity();

        List<Order> acceptedOrders = getAllAcceptedOrders(currentUserEntity.getId());
        orders.addAll(acceptedOrders);
        log.info("Accepted orders size={}", acceptedOrders.size());

        OrderPage orderPage = new OrderPage();
        orderPage.setContent(orders);
        orderPage.setTotalPages(orderEntitiesPage.getTotalPages());
        orderPage.setTotalElements(orderEntitiesPage.getTotalElements());
        orderPage.setSize(orderEntitiesPage.getSize());
        orderPage.setPageable(orderEntitiesPage.getPageable());
        orderPage.setNumberOfElements(orderEntitiesPage.getNumberOfElements());
        orderPage.setNumber(orderEntitiesPage.getNumber());
        orderPage.setEmpty(orderEntitiesPage.isEmpty());
        orderPage.setFirst(orderEntitiesPage.isFirst());

        log.info("Found order page={}.", orderPage);

        return orderPage;
    }

    @Override
    public List<Order> getAllCreatedOrders(UUID userId) {
        log.debug("Get all orders created by user with id={} .", userId);

        List<OrderEntity> orderEntities = orderEntityRepository.findAllByCreatorId(userId);

        log.debug("Found {} orders for creator={}.", orderEntities.size(), userId);

        return mapOrderEntitiesToOrders(orderEntities);
    }

    @Override
    public List<Order> getAllAcceptedOrders(UUID userId) {
        log.debug("Get all orders accepted by user with id={} .", userId);

        List<OrderEntity> orderEntities = orderEntityRepository.findAllByAcceptorId(userId);

        log.debug("Found {} orders for acceptor with id={}.", orderEntities.size(), userId);

        return mapOrderEntitiesToOrders(orderEntities);
    }

    @Override
    public List<Status> getOrderStatuses() {
        log.debug("Get all orders statuses.");

        List<Status> statuses = new ArrayList<>();
        List<StatusOrderEntity> statusOrderEntities = statusOrderEntityRepository.findAll();
        for (StatusOrderEntity statusOrderEntity : statusOrderEntities) {
            Status status = new Status();
            status.setName(statusOrderEntity.getName());
            statuses.add(status);
        }

        return statuses;
    }

    /**
     * Maps a list of OrderEntity objects to a list of Order objects, including user debugrmation for creator and acceptor.
     *
     * @param orderEntities The list of OrderEntity objects to map.
     * @return A list of Order objects with user debugrmation for creator and acceptor.
     */
    private List<Order> mapOrderEntitiesToOrders(List<OrderEntity> orderEntities) {
        List<Order> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            User creator = userService.getUserById(orderEntity.getCreatorId());
            User acceptor = userService.getUserById(orderEntity.getAcceptorId());

            Order order = orderEntity2OrderMapper.toOrder(orderEntity, creator, acceptor);
            orders.add(order);
        }
        return orders;
    }

}

