package com.xcdm.livein.service;

import com.xcdm.livein.dto.SearchHistoryReadDTO;
import com.xcdm.livein.entity.SearchHistory;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.interfaces.SearchHistoryService;
import com.xcdm.livein.interfaces.UserService;
import com.xcdm.livein.repo.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.mappers.SearchHistoryMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final UserService userService;
    private final SearchHistoryMapper searchHistoryMapper;

    @Override
    public List<SearchHistory> findByUser(User currentUser) {
        return searchHistoryRepository.findByUser(currentUser);
    }

    @Override
    public List<SearchHistoryReadDTO> getSearchHistory() {
        User currentUser = userService.getCurrentUser();
        List<SearchHistory> searchHistories = findByUser(currentUser);

        List<SearchHistoryReadDTO> searchHistoryReadDTOS = new ArrayList<>();
        searchHistories.forEach(sh -> {
            SearchHistoryReadDTO dto = searchHistoryMapper.toDto(sh);
            searchHistoryReadDTOS.add(dto);
        });
        return searchHistoryReadDTOS;
    }

    @Override
    public SearchHistory save(SearchHistory searchHistory) {
        return searchHistoryRepository.save(searchHistory);
    }
}
