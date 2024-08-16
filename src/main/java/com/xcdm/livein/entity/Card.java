package com.xcdm.livein.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Card extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String cardholderName;

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 4)
    private String cvv;

    private Date expirationDate;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
