package com.xcdm.livein.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemCreateDTO {

    private Integer productId;
//    private Product product;
    private Integer quantity;

}
