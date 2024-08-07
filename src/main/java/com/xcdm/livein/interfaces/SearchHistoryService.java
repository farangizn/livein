package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.SearchHistoryReadDTO;
import com.xcdm.livein.entity.SearchHistory;
import com.xcdm.livein.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchHistoryService {
    List<SearchHistory> findByUser(User currentUser);

    List<SearchHistoryReadDTO> getSearchHistory();

    SearchHistory save(SearchHistory searchHistory);
}
