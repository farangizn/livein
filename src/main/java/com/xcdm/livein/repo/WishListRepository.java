package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findByUser(User user);

    Optional<WishList> findByProductId(Integer productId);

    void deleteByProduct(Product product);
}