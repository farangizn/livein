package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.Catalog;
import com.xcdm.livein.entity.District;
import com.xcdm.livein.entity.Region;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppCategoryDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Date date;
    private String timeRange;
    private String comment;
    private Region region;
    private District district;
    private List<Catalog> category;
}
