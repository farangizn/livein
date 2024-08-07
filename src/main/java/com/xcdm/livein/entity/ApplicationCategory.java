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
public class ApplicationCategory extends BaseEntity {

    @ManyToOne
    private Application application;

    @ManyToOne
    private Catalog catalog;
}
