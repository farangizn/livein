package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.WishlistDTO;
import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.entity.WishList;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WishlistService {
    List<WishList> findByUser(User user);

    Optional<WishList> findByProductId(Integer productId);

    void deleteProductFromWishlist(Product product);

    PaginatedResponse<WishlistDTO> getWishList(int limit, int offset, HttpServletRequest request);

    HttpEntity<WishlistDTO> saveWishlist(Integer productId);

    HttpEntity<?> deleteWishlistProduct(Integer productId);

    HttpEntity<?> getWishlistByProductId(Integer productId);

}
