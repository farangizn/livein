package com.xcdm.livein.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    @NotNull
    private String date;

    @NotNull
    private String timeRange;

    private String comment;

    @NotNull
    private Integer regionId;

    @NotNull
    private Integer districtId;

//    private List<Catalog> catalogs;
    private List<Integer> catalogIds;

}
