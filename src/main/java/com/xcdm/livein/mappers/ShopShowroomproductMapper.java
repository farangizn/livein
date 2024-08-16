package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.ShowroomProductCreateDTO;
import com.xcdm.livein.entity.ShopShowroomproduct;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShopShowroomproductMapper {
    ShopShowroomproduct toEntity(ShowroomProductCreateDTO showroomProductCreateDTO);

    ShowroomProductCreateDTO toDto(ShopShowroomproduct shopShowroomproduct);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShopShowroomproduct partialUpdate(ShowroomProductCreateDTO showroomProductCreateDTO, @MappingTarget ShopShowroomproduct shopShowroomproduct);
}