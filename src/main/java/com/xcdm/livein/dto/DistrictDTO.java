package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {
    private String name;
    private String nameUz;
    private String nameOz;
    private String nameRu;
    private String nameEn;
    private Integer regionId;
//    private Region region;

}
