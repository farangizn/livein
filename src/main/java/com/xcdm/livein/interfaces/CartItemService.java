package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.CartItemCreateDTO;
import com.xcdm.livein.dto.CartItemDTO;
import com.xcdm.livein.dto.CartItemReadDTO;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartItemService {
    CartItem saveCartItem(CartItemCreateDTO cartItemCreateDTO);

    List<CartItem> findCartItemsByCart(Cart cart);

    Optional<CartItem> findById(Integer id);

    void delete(CartItem cartItem);

    HttpEntity<?> deleteById(Integer id);

    HttpEntity<?> getCartItem(Integer id);

    HttpEntity<PaginatedResponse<CartItemDTO>> getCartItems(int limit, int offset, HttpServletRequest request);
}
