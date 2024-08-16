package com.xcdm.livein.entity;

import com.xcdm.livein.entity.abs.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ShopShowroomproduct extends BaseEntity {

    private Double x;

    private Double y;

    @ManyToOne
    private ShowroomProduct showroomProduct;
}