package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemReadDTO {
    private Integer id;
//    private Product product;
    private Integer productId;
    private Integer quantity;

}
