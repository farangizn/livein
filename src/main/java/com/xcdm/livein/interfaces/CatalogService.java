package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.CatalogDTO;
import com.xcdm.livein.entity.Catalog;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface CatalogService {
    CatalogDTO mapToDto(Catalog catalog);

    PaginatedResponse<CatalogDTO> formCatalogsResult(int limit, int offset, HttpServletRequest request);
}
