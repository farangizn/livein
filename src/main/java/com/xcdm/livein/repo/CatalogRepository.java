package com.xcdm.livein.repo;

import com.xcdm.livein.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    List<Catalog> findByParentId(Integer parentId);
}