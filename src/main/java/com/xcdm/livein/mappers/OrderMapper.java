package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.OrderDTO;
import com.xcdm.livein.entity.Order;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    Order toEntity(OrderDTO orderDTO);

    OrderDTO toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDTO orderDTO, @MappingTarget Order order);
}