package com.xcdm.livein.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowroomOuterDTO {
    private List<ShowroomInnerPaginatedDTO> products;
}
