package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.DistrictDTO;
import com.xcdm.livein.entity.District;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DistrictMapper {
    District toEntity(DistrictDTO districtDTO);

    DistrictDTO toDto(District district);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    District partialUpdate(DistrictDTO districtDTO, @MappingTarget District district);
}