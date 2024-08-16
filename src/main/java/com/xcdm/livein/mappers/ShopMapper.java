package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.ShopDTO;
import com.xcdm.livein.entity.Shop;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShopMapper {
    Shop toEntity(ShopDTO shopDTO);

    ShopDTO toDto(Shop shop);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Shop partialUpdate(ShopDTO shopDTO, @MappingTarget Shop shop);
}