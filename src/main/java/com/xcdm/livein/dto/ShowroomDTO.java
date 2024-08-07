package com.xcdm.livein.dto;

import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.Shop;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowroomDTO {

    private Integer id;
    private List<Product> products;
    private String banner;
    private String position;
    private Shop shop;

}
