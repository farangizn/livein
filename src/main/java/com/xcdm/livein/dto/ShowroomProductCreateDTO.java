package com.xcdm.livein.dto;

import com.xcdm.livein.entity.Product;
import lombok.Data;

@Data
public class ShowroomProductCreateDTO {

    private Double x;
    private Double y;
    private Product product;

}
