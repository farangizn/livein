package com.xcdm.livein.entity;

import jakarta.persistence.Column;
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
public class SiteReview extends BaseEntity {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String text;

}