package com.xcdm.livein.repo;

import com.xcdm.livein.entity.ShopShowroomProduct;
import com.xcdm.livein.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopShowroomProductRepository extends JpaRepository<ShopShowroomProduct, Integer> {
    List<ShopShowroomProduct> findAllByShowroom(Showroom showroom);
}