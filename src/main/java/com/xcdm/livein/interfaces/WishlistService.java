package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.entity.WishList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WishlistService {
    List<WishList> findByUser(User user);

    Optional<WishList> findByProductId(Integer productId);

    void deleteProductFromWishlist(Product product);
}
