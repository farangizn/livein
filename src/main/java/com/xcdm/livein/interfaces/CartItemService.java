package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.CartItemSaveDTO;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartItemService {
    CartItem saveCartItem(CartItemSaveDTO cartItemSaveDTO);

    List<CartItem> findCartItemsByCart(Cart cart);

    Optional<CartItem> findById(Integer id);

    void delete(CartItem cartItem);
}
