package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistDTO {
    private Integer id;
//    private Product product;
    private Integer productId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
