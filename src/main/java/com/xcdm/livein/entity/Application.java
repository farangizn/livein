package com.xcdm.livein.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Application extends BaseEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    @NotNull
    private Date date;

    @NotNull
    @Column(length = 20)
    private String timeRange;

    private String comment;

    @NotNull
    @ManyToOne
    private Region region;

    @NotNull
    @ManyToOne
    private District district;



    @ManyToMany
    @Column(name = "applications_category")
    private List<Catalog> catalogs;


}
