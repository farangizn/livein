package com.xcdm.livein.controller;

import com.xcdm.livein.dto.*;
import com.xcdm.livein.entity.*;
import com.xcdm.livein.enums.Status;
import com.xcdm.livein.interfaces.*;
import com.xcdm.livein.mappers.*;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.CartItemRepository;
import com.xcdm.livein.repo.OrderItemRepository;
import com.xcdm.livein.repo.OrderRepository;
import com.xcdm.livein.repo.WishListRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    private final OrderService orderService;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final WishListRepository wishListRepository;
    private final WishlistService wishlistService;
    private final WishListMapper wishListMapper;


    @GetMapping
    public HttpEntity<UserProfileReadDTO> getProfile() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }

    @PostMapping
    public HttpEntity<UserProfileReadDTO> saveProfile(@RequestBody UserProfileCreateDTO userProfileCreateDTO) {
        User user = profileService.saveProfile(userProfileCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }

    @PutMapping
    public HttpEntity<UserProfileReadDTO> updateProfile(@RequestBody UserProfileCreateDTO userProfileCreateDTO) {
        User user = userService.findByEmail(userProfileCreateDTO.getEmail());
        profileService.updateProfile(user, userProfileCreateDTO);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping
    public HttpEntity<?> deleteProfile() {
        User currentUser = userService.getCurrentUser();
        userService.delete(currentUser);
        return ResponseEntity.status(204).build();
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("my-cart/items")
    public HttpEntity<PaginatedResponse<CartItemDTO>> getCartItems(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                       HttpServletRequest request) {
        List<CartItemDTO> cartItemDTOS = cartItemRepository.findAll()
                .stream()
                .map(cartItemMapper::toDto)
                .toList();

        String baseUrl = request.getRequestURI();

        PaginatedResponse<CartItemDTO> response = PaginationUtil.paginate(cartItemDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @PostMapping("my-cart/items")
    public HttpEntity<CartItemReadDTO> saveCartItems(@RequestBody CartItemSaveDTO cartItemSaveDTO) {
        CartItem cartItem = cartItemService.saveCartItem(cartItemSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemReadMapper.toDto(cartItem));
    }

    @GetMapping("my-cart/items/{id}")
    public HttpEntity<CartItemReadDTO> getMyCartItem(@PathVariable Integer id) {
        Optional<CartItem> cartItemOpt = cartItemService.findById(id);

        if (cartItemOpt.isPresent()) {
            CartItemReadDTO dto = cartItemReadMapper.toDto(cartItemOpt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("my-cart/items/{id}")
    public HttpEntity<?> deleteCartItem(@PathVariable Integer id) {
        Optional<CartItem> cartItemOptional = cartService.findById(id);
        if (cartItemOptional.isPresent()) {
            cartItemService.delete(cartItemOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("my-history") // list or single?
    public HttpEntity<List<SearchHistoryReadDTO>> getMyHistory() {
        return ResponseEntity.ok( searchHistoryService.getSearchHistory());
    }

    @PostMapping("my-history") /////////////////////////////////////
    public HttpEntity<SearchHistoryReadDTO> saveSearchHistory(@RequestParam String text) {
        User currentUser = userService.getCurrentUser();
        SearchHistory searchHistory = searchHistoryService.save(new SearchHistory(text, currentUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(searchHistoryMapper.toDto(searchHistory));
    }

    @GetMapping("my-orders")
    public HttpEntity<PaginatedResponse<OrderDTO>> getMyOrders(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                   @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                   HttpServletRequest request) {
        List<OrderDTO> orderDTOS = orderRepository.findAllByUser(userService.getCurrentUser())
                .stream()
                .map(orderMapper::toDto)
                .toList();

        String baseUrl = request.getRequestURI();

        PaginatedResponse<OrderDTO> response = PaginationUtil.paginate(orderDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @PostMapping("my-orders") // RAW
    public HttpEntity<OrderDTO> saveOrder() {
        User currentUser = userService.getCurrentUser();
        Cart currentUsersCart = cartService.findCurrentUsersCart();
        List<CartItem> cartItems = cartItemRepository.findByCart(currentUsersCart);

        Order order = Order.builder()
                .user(currentUser)
                .status(Status.NEW)
                .build();
        orderRepository.save(order);

        OrderDTO orderDTO = OrderDTO.builder().cartItems(cartItems).build();

        cartItems.forEach(c -> {
            OrderItem orderItem = OrderItem.builder().product(c.getProduct()).quantity(c.getQuantity()).order(order).build();
            orderItemRepository.save(orderItem);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @GetMapping("wishlist")
    public HttpEntity<PaginatedResponse<WishlistDTO>> getWishList(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                    @RequestParam(value = "offset", defaultValue = "0") int offset,
                                    HttpServletRequest request) {

        User currentUser = userService.getCurrentUser();
        List<WishList> wishLists = wishlistService.findByUser(currentUser);

        List<WishlistDTO> wishlistDTOS = wishListRepository.findByUser(userService.getCurrentUser())
                .stream()
                .map(wishListMapper::toDto)
                .toList();

        String baseUrl = request.getRequestURI();

        PaginatedResponse<WishlistDTO> response = PaginationUtil.paginate(wishlistDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);

    }

    @PostMapping("wishlist")
    public HttpEntity<WishlistDTO> saveWishlist(@RequestBody Product product) {
        User currentUser = userService.getCurrentUser();
        WishList wishList = WishList.builder().user(currentUser).product(product).build();
        wishListRepository.save(wishList);

        return ResponseEntity.ok(wishListMapper.toDto(wishList));
    }

    @GetMapping("wishlist/{product_id}")
    public HttpEntity<WishlistDTO> getWishlistByProductId(@PathVariable Integer product_id) {
        Optional<WishList> wishListOptional = wishlistService.findByProductId(product_id);

        return wishListOptional.map(wishList -> ResponseEntity.ok(wishListMapper.toDto(wishList))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("wishlist/{product_id}")
    public HttpEntity<?> deleteWishlist(@PathVariable Integer product_id) {
        Optional<Product> productOptional = profileService.findById(product_id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            wishlistService.deleteProductFromWishlist(product);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
