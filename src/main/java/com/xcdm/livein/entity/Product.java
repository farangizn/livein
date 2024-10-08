package com.xcdm.livein.entity;

import com.xcdm.livein.entity.abs.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product extends BaseEntity {

    private String name;

    private Double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigInteger quantity;

    @ManyToOne
    private Catalog catalog;

    @ManyToOne
    private Material material;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private Upholster upholster;

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(columnDefinition = "TEXT")
    private String descriptionUz;

    @Column(columnDefinition = "TEXT")
    private String descriptionRu;

    @Column(columnDefinition = "TEXT")
    private String descriptionCy;

    private String nameEn;
    private String nameUz;
    private String nameRu;
    private String nameCy;

    private Boolean isActive;

    private Boolean isFavorite;

    private String madeIn;

    @ManyToMany
    private List<Discount> discounts;

    @ManyToMany
    private List<Color> colors;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
