package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.SiteReviewResponse;
import org.springframework.stereotype.Service;

@Service
public interface SiteReviewService {
    SiteReviewResponse getSiteReviews(int limit, int offset);
}
