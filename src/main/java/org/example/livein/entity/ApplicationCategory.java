package org.example.livein.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ApplicationCategory extends BaseEntity {

    @ManyToOne
    private Application application;

    @ManyToOne
    private Catalog catalog;
}
