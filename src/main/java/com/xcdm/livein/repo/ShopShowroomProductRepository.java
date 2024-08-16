package com.xcdm.livein.repo;

import com.xcdm.livein.entity.ShopShowroomproduct;
import com.xcdm.livein.entity.ShowroomProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopShowroomProductRepository extends JpaRepository<ShopShowroomproduct, Integer> {

    List<ShopShowroomproduct> findAllByShowroomProduct(ShowroomProduct product);
}