package com.xcdm.livein.mappers;

import com.xcdm.livein.entity.SiteReview;
import com.xcdm.livein.dto.SiteReviewDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SiteReviewMapper {
    SiteReview toEntity(SiteReviewDTO siteReviewDTO);

    SiteReviewDTO toDto(SiteReview siteReview);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SiteReview partialUpdate(SiteReviewDTO siteReviewDTO, @MappingTarget SiteReview siteReview);
}