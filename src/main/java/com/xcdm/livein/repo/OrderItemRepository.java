package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Order;
import com.xcdm.livein.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}