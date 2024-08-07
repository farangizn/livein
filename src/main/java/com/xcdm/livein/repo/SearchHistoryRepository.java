package com.xcdm.livein.repo;

import com.xcdm.livein.entity.SearchHistory;
import com.xcdm.livein.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    List<SearchHistory> findByUser(User user);
}