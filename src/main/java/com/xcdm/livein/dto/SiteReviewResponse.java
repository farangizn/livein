package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteReviewResponse {
    private int count;
    private String next;
    private String previous;
    private List<SiteReviewDTO> results;
}
