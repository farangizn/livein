package com.xcdm.livein.entity;

import com.xcdm.livein.entity.abs.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Catalog extends BaseEntity {

    private String name;

    private String nameEn;

    private String nameRu;

    private String nameUz;

    private String nameCy;

    @Column(length = 100)
    private String banner;

    private Integer parentId;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
