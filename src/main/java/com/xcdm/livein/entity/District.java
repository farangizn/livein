package com.xcdm.livein.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class District extends BaseEntity {

    private String name;
    private String nameUz;
    private String nameOz;
    private String nameRu;
    private String nameEn;

    @ManyToOne
    private Region region;

}
