package org.example.livein.repo;

import org.example.livein.entity.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint, Integer> {
}