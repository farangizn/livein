package com.xcdm.livein.service;

import com.xcdm.livein.interfaces.CartItemService;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.dto.CartItemSaveDTO;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import com.xcdm.livein.interfaces.CartService;
import com.xcdm.livein.repo.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem saveCartItem(CartItemSaveDTO cartItemSaveDTO) {
        return CartItem.builder()
                .cart(cartService.findCurrentUsersCart())
                .product(cartItemSaveDTO.getProduct())
                .quantity(cartItemSaveDTO.getQuantity())
                .build();
    }

    @Override
    public List<CartItem> findCartItemsByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public Optional<CartItem> findById(Integer id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

}
