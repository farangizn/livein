package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Order;
import com.xcdm.livein.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
}