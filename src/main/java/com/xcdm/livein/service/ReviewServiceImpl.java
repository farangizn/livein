package com.xcdm.livein.service;

import com.xcdm.livein.entity.Review;
import com.xcdm.livein.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.repo.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Optional<Review> findByProductId(Integer productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review save(Review entity) {
        return reviewRepository.save(entity);
    }
}
