package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.ShowroomDTO;
import com.xcdm.livein.entity.Showroom;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShowroomMapper {
    Showroom toEntity(ShowroomDTO showroomDTO);

    ShowroomDTO toDto(Showroom showroom);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Showroom partialUpdate(ShowroomDTO showroomDTO, @MappingTarget Showroom showroom);
}