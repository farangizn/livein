package com.xcdm.livein.dto;

import com.xcdm.livein.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {
    private String name;
    private String nameUz;
    private String nameOz;
    private String nameRu;
    private String nameEn;
//    private Integer regionId;
    private Region region;

}
