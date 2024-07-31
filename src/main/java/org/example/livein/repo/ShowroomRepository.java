package org.example.livein.repo;

import org.example.livein.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowroomRepository extends JpaRepository<Showroom, Integer> {
}