package com.xcdm.livein.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.entity.abs.BaseEntity;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Application extends BaseEntity {

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    private Date date;

    @Column(length = 20)
    private String timeRange;

    private String comment;

    @ManyToOne
    private Region region;

    @ManyToOne
    private District district;



    @ManyToMany
    @Column(name = "applications_category")
    private List<Catalog> catalogs;


}
