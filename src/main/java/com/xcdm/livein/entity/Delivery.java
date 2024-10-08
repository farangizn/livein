package com.xcdm.livein.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;
import com.xcdm.livein.enums.DeliveryType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Delivery extends BaseEntity {

    private String name;
    private String phone;
    private String address;
    private String entrance;
    private String floor;
    private String apartment;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 25)
    private DeliveryType deliveryType;

    private Date deliveryDate;

    private Boolean isDelivered;

    @ManyToOne
    private Order order;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
