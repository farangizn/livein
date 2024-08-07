package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.CartItemReadDTO;
import com.xcdm.livein.entity.CartItem;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemReadMapper {
    CartItem toEntity(CartItemReadDTO cartItemReadDTO);

    CartItemReadDTO toDto(CartItem cartItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartItem partialUpdate(CartItemReadDTO cartItemReadDTO, @MappingTarget CartItem cartItem);
}