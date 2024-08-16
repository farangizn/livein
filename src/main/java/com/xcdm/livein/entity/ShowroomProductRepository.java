package com.xcdm.livein.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowroomProductRepository extends JpaRepository<ShowroomProduct, Integer> {
    List<ShowroomProduct> findAllByShowroomId(Integer showroomId);
}