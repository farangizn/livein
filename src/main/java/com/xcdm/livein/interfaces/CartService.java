package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.CartDTO;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {

    Cart findCurrentUsersCart();

    List<CartItem> findCartItems(Cart cart);

    CartDTO formCartDTO(Cart cart);

    void delete(Cart cart);

    Optional<CartItem> findById(Integer id);
}
