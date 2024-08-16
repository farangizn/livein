package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByProductId(Integer productId);

    Optional<Review> findByProductId(Integer productId);
}