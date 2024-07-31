package org.example.livein.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SiteReviews extends BaseEntity {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String text;

}