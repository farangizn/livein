package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.CallRequestDTO;
import com.xcdm.livein.entity.CallRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CallRequestMapper {
    CallRequest toEntity(CallRequestDTO callRequestDTO);

    CallRequestDTO toDto(CallRequest callRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CallRequest partialUpdate(CallRequestDTO callRequestDTO, @MappingTarget CallRequest callRequest);
}