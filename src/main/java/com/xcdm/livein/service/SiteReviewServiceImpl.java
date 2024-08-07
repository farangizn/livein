package com.xcdm.livein.service;

import com.xcdm.livein.entity.SiteReview;
import com.xcdm.livein.interfaces.SiteReviewService;
import com.xcdm.livein.repo.SiteReviewRepository;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.dto.SiteReviewDTO;
import com.xcdm.livein.dto.SiteReviewResponse;
import com.xcdm.livein.mappers.SiteReviewMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteReviewServiceImpl implements SiteReviewService {

    private final SiteReviewRepository siteReviewRepository;
    private final SiteReviewMapper siteReviewMapper;

    @Override
    public SiteReviewResponse getSiteReviews(int limit, int offset) {
        // Retrieve the total count of reviews
        int totalReviews = (int) siteReviewRepository.count();

        // Retrieve reviews using limit and offset
        Pageable pageable = PageRequest.of(offset / limit, limit);
        List<SiteReview> reviews = siteReviewRepository.findAll(pageable).getContent();

        List<SiteReviewDTO> reviewDTOs = reviews.stream()
                .map(siteReviewMapper::toDto)
                .toList();

        SiteReviewResponse response = new SiteReviewResponse();
        response.setCount(totalReviews);
        response.setNext(offset + limit < totalReviews ? "url to next page" : null);
        response.setPrevious(offset > 0 ? "url to previous page" : null);
        response.setResults(reviewDTOs);

        return response;
    }
}
