package com.xcdm.livein.service;

import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.entity.WishList;
import com.xcdm.livein.interfaces.WishlistService;
import com.xcdm.livein.repo.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishListRepository wishListRepository;

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
}
