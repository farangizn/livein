package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShowroomInnerDTO {
    private Double x;
    private Double y;
    private Integer productId; // showroomProductId
}
