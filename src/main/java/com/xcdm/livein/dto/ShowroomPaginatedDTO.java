package com.xcdm.livein.dto;

import com.xcdm.livein.pagination.PaginatedResponse;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class ShowroomPaginatedDTO {
    private PaginatedResponse<ShowroomInnerPaginatedDTO> showroomProducts;
}
