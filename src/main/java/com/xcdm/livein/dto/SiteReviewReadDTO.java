package com.xcdm.livein.dto;

import com.xcdm.livein.entity.SiteReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteReviewReadDTO {
    private Integer count;
    private String next;
    private String previous;
    private List<SiteReview> results;
}
