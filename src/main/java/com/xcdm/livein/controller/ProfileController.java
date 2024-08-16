package com.xcdm.livein.controller;

import com.xcdm.livein.dto.*;
import com.xcdm.livein.entity.*;
import com.xcdm.livein.entity.abs.BaseEntity;
import com.xcdm.livein.interfaces.*;
import com.xcdm.livein.mappers.*;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.repo.CartItemRepository;
import com.xcdm.livein.repo.CartRepository;
import com.xcdm.livein.repo.UserRepository;
import com.xcdm.livein.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartItemReadMapper cartItemReadMapper;
    private final SearchHistoryService searchHistoryService;
    private final SearchHistoryMapper searchHistoryMapper;
    private final WishlistService wishlistService;
    private final OrderService orderService;
    private final AuthService authService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


    @GetMapping
    public HttpEntity<UserProfileReadDTO> getProfile() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }

    @PostMapping
    public HttpEntity<UserProfileReadDTO> saveProfile(@RequestBody @Valid UserProfileCreateDTO userProfileCreateDTO) {
        User user = profileService.saveProfile(userProfileCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }

    @PutMapping
    public HttpEntity<UserProfileReadDTO> updateProfile(@RequestBody @Valid UserProfileCreateDTO userProfileCreateDTO) {
        User user = userService.findByEmail(userProfileCreateDTO.getEmail());
        profileService.updateProfile(user, userProfileCreateDTO);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping
    public HttpEntity<?> deleteProfile() {
//        User currentUser = userService.getCurrentUser();
//        userRepository.delete(currentUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("my-cart")
    public HttpEntity<CartDTO> getMyCart() {
        Cart cart = cartService.findCurrentUsersCart();
        return ResponseEntity.ok(cartService.formCartDTO(cart));
    }

    @DeleteMapping("my-cart")
    public HttpEntity<?> deleteMyCart() {
        Cart currentUsersCart = cartService.findCurrentUsersCart();
        cartService.delete(currentUsersCart);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cart deleted successfully");
    }

    @GetMapping("my-cart/items")
    public HttpEntity<PaginatedResponse<CartItemDTO>> getCartItems(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                   @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                   HttpServletRequest request) {
        return cartItemService.getCartItems(limit, offset, request);
    }

    @PostMapping("my-cart/items")
    public HttpEntity<?> saveCartItems(@RequestBody CartItemCreateDTO cartItemCreateDTO) {
        CartItem cartItem = cartItemService.saveCartItem(cartItemCreateDTO);
        if (cartItem != null) {
            CartItemReadDTO dto = cartItemReadMapper.toDto(cartItem);
            dto.setProductId(cartItem.getProduct().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.badRequest().body("Product with id " + cartItemCreateDTO.getProductId() + " does not exist");
        }
    }

    @GetMapping("my-cart/items/{id}")
    public HttpEntity<?> getMyCartItem(@PathVariable Integer id) {
        return cartItemService.getCartItem(id);
    }

    @DeleteMapping("my-cart/items/{id}")
    public HttpEntity<?> deleteCartItem(@PathVariable Integer id) {
        return cartItemService.deleteById(id);
    }

    @GetMapping("my-history")
    public HttpEntity<List<SearchHistoryReadDTO>> getMyHistory() {
        return ResponseEntity.ok( searchHistoryService.getSearchHistory());
    }

    @PostMapping("my-history")
    public HttpEntity<SearchHistoryReadDTO> saveSearchHistory(@RequestParam String text) {
        User currentUser = userService.getCurrentUser();
        SearchHistory searchHistory = searchHistoryService.save(new SearchHistory(text, currentUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(searchHistoryMapper.toDto(searchHistory));
    }

    @GetMapping("my-orders")
    public HttpEntity<PaginatedResponse<OrderDTO>> getMyOrders(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                   @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                   HttpServletRequest request) {
        return ResponseEntity.ok(orderService.getOrdersAndFormResult(limit, offset, request));
    }

    @PostMapping("my-orders")
    public HttpEntity<OrderDTO> saveOrder() {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder());
    }

    @PostMapping("/set-password")
    public HttpEntity<?> setPassword(@RequestBody @Valid PasswordDTO passwordDTO) {
        return authService.changePassword(passwordDTO);
    }

    @GetMapping("wishlist")
    public HttpEntity<PaginatedResponse<WishlistDTO>> getWishList(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                    @RequestParam(value = "offset", defaultValue = "0") int offset,
                                    HttpServletRequest request) {

        return ResponseEntity.ok(wishlistService.getWishList(limit, offset, request));

    }

    @PostMapping("wishlist")
    public HttpEntity<WishlistDTO> saveWishlist(@RequestParam Integer productId) {
        return wishlistService.saveWishlist(productId);
    }

    @GetMapping("wishlist/{product_id}")
    public HttpEntity<?> getWishlistByProductId(@PathVariable Integer product_id) {
        return wishlistService.getWishlistByProductId(product_id);
    }

    @DeleteMapping("wishlist/{product_id}")
    public HttpEntity<?> deleteWishlist(@PathVariable Integer product_id) {
        return wishlistService.deleteWishlistProduct(product_id);
    }


}
