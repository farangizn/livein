package org.example.livein.repo;

import org.example.livein.entity.ApplicationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationCategoryRepository extends JpaRepository<ApplicationCategory, Integer> {
}