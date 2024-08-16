package com.xcdm.livein.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowroomDTO {

    private Integer id;
    private List<String> products;
    private String banner;
    private String position;
    private Integer shopId;

}
