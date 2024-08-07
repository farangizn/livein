package com.xcdm.livein.entity;

import com.xcdm.livein.entity.abs.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SearchHistory extends BaseEntity {

    private String text;

    @ManyToOne
    private User user;
}
