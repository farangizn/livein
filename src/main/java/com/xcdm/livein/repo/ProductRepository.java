package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}