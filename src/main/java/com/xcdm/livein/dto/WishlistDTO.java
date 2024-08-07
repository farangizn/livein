package com.xcdm.livein.dto;

import com.xcdm.livein.entity.Product;
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
    private Product product;
//    private Integer product;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
