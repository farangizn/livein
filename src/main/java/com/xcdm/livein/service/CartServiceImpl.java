package com.xcdm.livein.service;

import com.xcdm.livein.dto.CartDTO;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.dto.CartItemCountDTO;
import com.xcdm.livein.dto.CartItemDTO;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.interfaces.CartService;
import com.xcdm.livein.interfaces.UserService;
import com.xcdm.livein.repo.CartItemRepository;
import com.xcdm.livein.repo.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Override
    public Cart findCurrentUsersCart() {
        User currentUser = userService.getCurrentUser();
        return cartRepository.findByUser(currentUser);
    }

    @Override
    public List<CartItem> findCartItems(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public CartDTO formCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());

        List<CartItem> cartItems = findCartItems(cart);

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
//            cartItemDTO.setProduct(cartItem.getProduct().getId());
            cartItemDTOs.add(cartItemDTO);
        }

        cartDTO.setItems(cartItemDTOs);
        return cartDTO;
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public Optional<CartItem> findById(Integer id) {
        return cartItemRepository.findById(id);
    }

//    @Override
//    public CartItemCountDTO formCartItemCountDTO(Cart cart) {
//
//        List<CartItem> cartItems = findCartItems(cart);
//
//        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
//        cartItems.forEach(cartItem ->  {
//            CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);
//            cartItemDTOs.add(cartItemDTO);
//        });
//
//        return CartItemCountDTO.builder()
//                .results(cartItemDTOs)
//                .count(cartItemDTOs.size())
//                .build();
//    }
}
