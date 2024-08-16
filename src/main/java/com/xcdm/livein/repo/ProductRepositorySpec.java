package com.xcdm.livein.repo;


import com.xcdm.livein.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepositorySpec extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
}