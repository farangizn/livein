package com.xcdm.livein.dto;

import com.xcdm.livein.enums.Rate;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewCreateDTO {
    @NotNull
    private Rate rate;
    @NotNull
    private String text;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer productId;
}
