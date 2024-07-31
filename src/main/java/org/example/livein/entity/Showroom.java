package org.example.livein.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Showroom extends BaseEntity {

    @Column(length = 100)
    private String banner;

    @ManyToOne
    private Shop shop;

    private String position;

    @ManyToMany
    private List<Product> products;
}
