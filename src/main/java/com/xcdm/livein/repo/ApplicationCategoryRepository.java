package com.xcdm.livein.repo;

import com.xcdm.livein.entity.ApplicationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationCategoryRepository extends JpaRepository<ApplicationCategory, Integer> {
}