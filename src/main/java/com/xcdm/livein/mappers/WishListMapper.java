package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.WishlistDTO;
import com.xcdm.livein.entity.WishList;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WishListMapper {
    WishList toEntity(WishlistDTO wishlistDTO);

    WishlistDTO toDto(WishList wishList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WishList partialUpdate(WishlistDTO wishlistDTO, @MappingTarget WishList wishList);
}