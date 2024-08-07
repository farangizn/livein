package com.xcdm.livein.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Faq extends BaseEntity {

    private String title;
    private String titleUz;
    private String titleOz;
    private String titleRu;
    private String titleEn;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String descriptionUz;

    @Column(columnDefinition = "TEXT")
    private String descriptionOz;

    @Column(columnDefinition = "TEXT")
    private String descriptionRu;

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

}
