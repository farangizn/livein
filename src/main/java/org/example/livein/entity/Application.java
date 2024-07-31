package org.example.livein.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private District district;

    @ManyToOne
    private Region region;

//
//    @ManyToMany
//    @Column(name = "applications_category")
//    private List<Catalog> catalogs;


}
