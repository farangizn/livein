package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.CallRequestCreateDTO;
import com.xcdm.livein.entity.CallRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CallRequestMapper {
    CallRequest toEntity(CallRequestCreateDTO callRequestCreateDTO);

    CallRequestCreateDTO toDto(CallRequest callRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CallRequest partialUpdate(CallRequestCreateDTO callRequestCreateDTO, @MappingTarget CallRequest callRequest);
}