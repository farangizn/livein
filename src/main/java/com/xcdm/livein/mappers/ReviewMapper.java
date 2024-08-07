package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.ReviewDTO;
import com.xcdm.livein.entity.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    Review toEntity(ReviewDTO reviewDTO);

    ReviewDTO toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review partialUpdate(ReviewDTO reviewDTO, @MappingTarget Review review);
}