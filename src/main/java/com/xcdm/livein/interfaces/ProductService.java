package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findProducts(String name, String price, String minPrice, String maxPrice, String catalog, String room, String colorsHexCode, String material, String shop, String random, String orderBy, int limit, int offset);
}
