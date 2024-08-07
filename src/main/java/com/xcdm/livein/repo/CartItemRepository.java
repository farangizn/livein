package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findById(Integer id);

}