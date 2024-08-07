package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUser(User user);
}