package com.xcdm.livein.repo;

import com.xcdm.livein.entity.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint, Integer> {
}