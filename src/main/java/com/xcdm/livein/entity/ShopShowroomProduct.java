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
public class ShopShowroomProduct extends BaseEntity {

    private Number x;
    private Number y;

    @ManyToOne
    private Product product;


}
