package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}