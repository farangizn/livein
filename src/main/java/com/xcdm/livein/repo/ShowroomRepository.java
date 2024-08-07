package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowroomRepository extends JpaRepository<Showroom, Integer> {

    List<Showroom> findByPosition(String position);
}