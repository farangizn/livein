package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.CartItemDTO;
import com.xcdm.livein.entity.CartItem;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    CartItem toEntity(CartItemDTO cartItemDTO);

    CartItemDTO toDto(CartItem cartItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartItem partialUpdate(CartItemDTO cartItemDTO, @MappingTarget CartItem cartItem);
}