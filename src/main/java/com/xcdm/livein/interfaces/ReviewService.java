package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.ReviewCreateDTO;
import com.xcdm.livein.dto.ReviewDTO;
import com.xcdm.livein.entity.Review;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {

    List<Review> findByProductId(Integer productId);

    Review save(Review entity);

    HttpEntity<PaginatedResponse<ReviewDTO>> formProductReview(int limit, int offset, HttpServletRequest request);

    HttpEntity<ReviewDTO> saveProductReview(ReviewCreateDTO reviewCreateDTO);

    HttpEntity<List<ReviewDTO>> getReviewByProductId(Integer productId);
}
