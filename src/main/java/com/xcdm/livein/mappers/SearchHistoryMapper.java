package com.xcdm.livein.mappers;

import com.xcdm.livein.dto.SearchHistoryReadDTO;
import com.xcdm.livein.entity.SearchHistory;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SearchHistoryMapper {
    SearchHistory toEntity(SearchHistoryReadDTO searchHistoryReadDTO);

    SearchHistoryReadDTO toDto(SearchHistory searchHistory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SearchHistory partialUpdate(SearchHistoryReadDTO searchHistoryReadDTO, @MappingTarget SearchHistory searchHistory);
}