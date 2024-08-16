package com.xcdm.livein.service;

import com.xcdm.livein.dto.WishlistDTO;
import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.entity.WishList;
import com.xcdm.livein.interfaces.ProfileService;
import com.xcdm.livein.interfaces.UserService;
import com.xcdm.livein.interfaces.WishlistService;
import com.xcdm.livein.mappers.WishListMapper;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.ProductRepository;
import com.xcdm.livein.repo.WishListRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishListRepository wishListRepository;
    private final UserService userService;
    private final WishListMapper wishListMapper;
    private final ProductRepository productRepository;
    private final ProfileService profileService;

    @Override
    public List<WishList> findByUser(User user) {
        return wishListRepository.findByUser(user);
    }

    @Override
    public Optional<WishList> findByProductId(Integer productId) {
        return wishListRepository.findByProductId(productId);
    }

    @Override
    public void deleteProductFromWishlist(Product product) {
        wishListRepository.deleteByProduct(product);
    }

    @Override
    public PaginatedResponse<WishlistDTO> getWishList(int limit, int offset, HttpServletRequest request) {
        List<WishList> wishLists = wishListRepository.findByUser(userService.getCurrentUser());
        List<WishlistDTO> wishlistDTOS = wishLists
                .stream()
                .map(wishList -> {
                    WishlistDTO dto = wishListMapper.toDto(wishList);
                    dto.setProductId(wishList.getProduct().getId());
                    return dto;
                })
                .toList();


        String baseUrl = request.getRequestURI();

        return PaginationUtil.paginate(wishlistDTOS, limit, offset, baseUrl);
    }

    @Override
    public HttpEntity<WishlistDTO> saveWishlist(Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            User currentUser = userService.getCurrentUser();
            if (wishListRepository.findByProductId(productId).isEmpty()) {
                WishList wishList = WishList.builder().user(currentUser).createdAt(ZonedDateTime.now()).product(productOptional.get()).build();
                wishListRepository.save(wishList);
                WishlistDTO dto = wishListMapper.toDto(wishList);
                dto.setProductId(productId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public HttpEntity<?> deleteWishlistProduct(Integer productId) {
        Optional<Product> productOptional = profileService.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            deleteProductFromWishlist(product);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.badRequest().body("Wishlist product under id " + productId + " does not exist");
        }
    }

    @Override
    public HttpEntity<?> getWishlistByProductId(Integer productId) {
        Optional<WishList> wishListOptional = findByProductId(productId);

        if (wishListOptional.isPresent()) {
            WishList wishList = wishListOptional.get();
            WishlistDTO dto = wishListMapper.toDto(wishList);
            dto.setProductId(productId);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.badRequest().body("Product with the given id is not present in wishlist");
        }
    }
}
