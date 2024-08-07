package com.xcdm.livein.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemSaveDTO {

    private Product product;
//    private Integer product;
    private Integer quantity;

}
