package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.CallRequestReadDTO;
import com.xcdm.livein.entity.CallRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CallRequestReadMapper {
    CallRequest toEntity(CallRequestReadDTO callRequestReadDTO);

    CallRequestReadDTO toDto(CallRequest callRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CallRequest partialUpdate(CallRequestReadDTO callRequestReadDTO, @MappingTarget CallRequest callRequest);
}