package com.xcdm.livein.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallRequestCreateDTO {
    private String name;
    private String phone;
    private Integer productId;
//    private Product product;
}
