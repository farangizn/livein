package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}