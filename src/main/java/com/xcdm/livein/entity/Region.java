package com.xcdm.livein.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Region extends BaseEntity {

    private String name;
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private String nameOz;

}