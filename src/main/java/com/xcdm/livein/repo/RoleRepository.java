package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}