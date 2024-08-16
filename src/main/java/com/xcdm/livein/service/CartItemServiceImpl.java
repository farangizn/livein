package com.xcdm.livein.service;

import com.xcdm.livein.dto.CartItemCreateDTO;
import com.xcdm.livein.dto.CartItemDTO;
import com.xcdm.livein.dto.CartItemReadDTO;
import com.xcdm.livein.entity.Product;
import com.xcdm.livein.interfaces.CartItemService;
import com.xcdm.livein.mappers.CartItemMapper;
import com.xcdm.livein.mappers.CartItemReadMapper;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.CartItem;
import com.xcdm.livein.interfaces.CartService;
import com.xcdm.livein.repo.CartItemRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartItemReadMapper cartItemReadMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItem saveCartItem(CartItemCreateDTO cartItemSaveDTO) {
        Optional<Product> productOptional = productRepository.findById(cartItemSaveDTO.getProductId());
        if (productOptional.isPresent()) {
            CartItem cartItem = CartItem.builder()
                    .cart(cartService.findCurrentUsersCart())
                    .product(productOptional.get())
                    .quantity(cartItemSaveDTO.getQuantity())
                    .build();
            return cartItemRepository.save(cartItem);
        } else {
            return null;
        }
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

    @Override
    public HttpEntity<?> deleteById(Integer id) {
        Optional<CartItem> cartItemOptional = cartService.findById(id);
        if (cartItemOptional.isPresent()) {
            delete(cartItemOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.badRequest().body("Cart item under id " + id + " does not exist");
        }
    }

    @Override
    public HttpEntity<?> getCartItem(Integer id) {
        Optional<CartItem> cartItemOpt = findById(id);

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            CartItemReadDTO dto = cartItemReadMapper.toDto(cartItem);
            dto.setProductId(cartItem.getProduct().getId());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.badRequest().body("Cart item under id " + id + " does not exist");
        }
    }

    @Override
    public HttpEntity<PaginatedResponse<CartItemDTO>> getCartItems(int limit, int offset, HttpServletRequest request) {
        List<CartItemDTO> cartItemDTOS = cartItemRepository.findAll()
                .stream()
                .map(cartItem -> {
                    CartItemDTO dto = cartItemMapper.toDto(cartItem);
                    dto.setProductId(cartItem.getProduct().getId());
                    return dto;
                })
                .toList();

        String baseUrl = request.getRequestURI();

        PaginatedResponse<CartItemDTO> response = PaginationUtil.paginate(cartItemDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

}
