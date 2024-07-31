package org.example.livein.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Region extends BaseEntity {

    private String name;
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private String nameOz;

}
