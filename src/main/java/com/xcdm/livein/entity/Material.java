package com.xcdm.livein.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Material extends BaseEntity {

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;
    private String nameCy;

}
