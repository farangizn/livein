package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.ReviewCreateDTO;
import com.xcdm.livein.entity.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewCreateMapper {
    Review toEntity(ReviewCreateDTO reviewCreateDTO);

    ReviewCreateDTO toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review partialUpdate(ReviewCreateDTO reviewCreateDTO, @MappingTarget Review review);
}