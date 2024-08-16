package com.xcdm.livein.service;

import com.xcdm.livein.dto.CartItemDTO;
import com.xcdm.livein.dto.ReviewCreateDTO;
import com.xcdm.livein.dto.ReviewDTO;
import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.Review;
import com.xcdm.livein.mappers.ReviewCreateMapper;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.interfaces.ReviewService;
import com.xcdm.livein.mappers.ReviewMapper;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.ProductRepository;
import com.xcdm.livein.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.repo.ReviewRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewCreateMapper reviewCreateMapper;
    private final ReviewMapper reviewMapper;

    @Override
    public List<Review> findByProductId(Integer productId) {
        return reviewRepository.findAllByProductId(productId);
    }

    @Override
    public Review save(Review entity) {
        return reviewRepository.save(entity);
    }

    @Override
    public HttpEntity<PaginatedResponse<ReviewDTO>> formProductReview(int limit, int offset, HttpServletRequest request) {
        String baseUrl = request.getRequestURI();

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Review review : reviewRepository.findAll()) {
            ReviewDTO reviewDTO = ReviewDTO.builder()
                    .id(review.getId())
                    .userId(review.getUser().getId())
                    .productId(review.getProduct().getId())
                    .rate(review.getRate())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .text(review.getText())
                    .build();
            reviewDTOS.add(reviewDTO);
        }
        PaginatedResponse<ReviewDTO> response = PaginationUtil.paginate(reviewDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<ReviewDTO> saveProductReview(ReviewCreateDTO reviewCreateDTO) {
        Optional<User> userOptional = userRepository.findById(reviewCreateDTO.getUserId());
        Optional<Product> productOptional = productRepository.findById(reviewCreateDTO.getProductId());
        Review review = reviewCreateMapper.toEntity(reviewCreateDTO);
        if (productOptional.isPresent() && userOptional.isPresent()) {
            review.setProduct(productOptional.get());
            review.setUser(userOptional.get());
            reviewRepository.save(review);
            ReviewDTO dto = reviewMapper.toDto(review);
            dto.setProductId(review.getProduct().getId());
            dto.setUserId(review.getUser().getId());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public HttpEntity<List<ReviewDTO>> getReviewByProductId(Integer productId) {
        List<ReviewDTO> reviews = reviewRepository.findAllByProductId(productId)
                .stream()
                .map(review -> {
                    ReviewDTO dto = reviewMapper.toDto(review);
                    dto.setUserId(review.getUser().getId());
                    dto.setProductId(review.getProduct().getId());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(reviews);
    }
}
