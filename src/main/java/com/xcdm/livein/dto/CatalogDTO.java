package com.xcdm.livein.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CatalogDTO {
    private Integer id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String name;
    private String nameEn;
    private String nameRu;
    private String nameUz;
    private String nameCy;
    private String banner;
    private List<CatalogDTO> children;
}
