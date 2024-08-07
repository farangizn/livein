package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemCountDTO {
    private Integer count;
    private String next;
    private String previous;
    private List<CartItemDTO> results;
}
