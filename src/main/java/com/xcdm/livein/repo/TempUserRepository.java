package com.xcdm.livein.repo;

import com.xcdm.livein.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TempUser, Integer> {
    TempUser findByEmail(String email);
}