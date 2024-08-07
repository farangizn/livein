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
public class Color extends BaseEntity {

    private String name;
    private String nameEn;
    private String nameUz;
    private String nameRu;
    private String nameCy;
    private String hexCode;

}
