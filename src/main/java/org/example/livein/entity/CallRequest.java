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
public class CallRequest extends BaseEntity {

    private String name;
    private String phone;

    @ManyToOne
    private Product product;

}
