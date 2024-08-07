package com.xcdm.livein.dto;

import com.xcdm.livein.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Integer id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<CartItem> cartItems;
}