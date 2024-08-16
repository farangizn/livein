package com.xcdm.livein.dto;

import com.xcdm.livein.enums.Rate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ReviewDTO {

    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private Rate rate;

    private String text;

    private Integer productId;

    private Integer userId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}
