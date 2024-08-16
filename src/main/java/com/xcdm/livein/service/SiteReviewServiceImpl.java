package com.xcdm.livein.service;

import com.xcdm.livein.entity.SiteReview;
import com.xcdm.livein.interfaces.SiteReviewService;
import com.xcdm.livein.pagination.PaginationUtil;
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
public class SiteReviewServiceImpl implements SiteReviewService {

}
