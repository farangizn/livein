package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Review;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ReviewService {

    Optional<Review> findByProductId(Integer productId);

    Review save(Review entity);
}
