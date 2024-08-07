package com.xcdm.livein.dto;

import com.xcdm.livein.entity.Product;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallRequestDTO {
    private String name;
    private String phone;
    @ManyToOne
    private Product product;
}
