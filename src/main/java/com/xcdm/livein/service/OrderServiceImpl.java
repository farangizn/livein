package com.xcdm.livein.service;

import com.xcdm.livein.dto.OrderDTO;
import com.xcdm.livein.dto.OrderItemDTO;
import com.xcdm.livein.entity.*;
import com.xcdm.livein.enums.Status;
import com.xcdm.livein.interfaces.CartService;
import com.xcdm.livein.interfaces.UserService;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.CartItemRepository;
import com.xcdm.livein.repo.OrderItemRepository;
import com.xcdm.livein.repo.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.interfaces.OrderService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    public OrderDTO saveOrder() {
        User currentUser = userService.getCurrentUser();
        Cart currentUsersCart = cartService.findCurrentUsersCart();
        List<CartItem> cartItems = cartItemRepository.findByCart(currentUsersCart);
        Order order = Order.builder()
                .user(currentUser)
                .createdAt(ZonedDateTime.now())
                .status(Status.NEW)
                .build();
        orderRepository.save(order);
        List<OrderItemDTO> orderItems = new ArrayList<>();
        cartItems.forEach(c -> {
            OrderItem orderItem = OrderItem.builder().product(c.getProduct()).quantity(c.getQuantity()).order(order).build();
            orderItemRepository.save(orderItem);
            orderItems.add(new OrderItemDTO(orderItem.getId()));
            cartItemRepository.delete(c);
        });

        return OrderDTO.builder().createdAt(order.getCreatedAt()).cartItems(orderItems).build();
        }

    @Override
    public PaginatedResponse<OrderDTO> getOrdersAndFormResult(int limit, int offset, HttpServletRequest request) {
        List<Order> orders = orderRepository.findAllByUser(userService.getCurrentUser());
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(order.getId())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                orderItemDTOS.add(new OrderItemDTO(orderItem.getId()));
            }
            orderDTO.setCartItems(orderItemDTOS);
            orderDTOS.add(orderDTO);
        }

        String baseUrl = request.getRequestURI();

        return PaginationUtil.paginate(orderDTOS, limit, offset, baseUrl);
    }
}
