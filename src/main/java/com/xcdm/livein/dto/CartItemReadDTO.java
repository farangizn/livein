package com.xcdm.livein.dto;

import com.xcdm.livein.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemReadDTO {
    private Integer id;
    private Product product;
//    private Integer product;
    private Integer quantity;

}
