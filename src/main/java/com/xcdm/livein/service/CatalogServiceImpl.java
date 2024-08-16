package com.xcdm.livein.service;

import com.xcdm.livein.dto.CatalogDTO;
import com.xcdm.livein.entity.Catalog;
import com.xcdm.livein.interfaces.CatalogService;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.CatalogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public PaginatedResponse<CatalogDTO> formCatalogsResult(int limit, int offset, HttpServletRequest request) {
        List<Catalog> catalogs = catalogRepository.findAll();

        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        for (Catalog catalog : catalogs) {
            CatalogDTO catalogDTO = mapToDto(catalog);
            catalogDTOs.add(catalogDTO);
        }

        String baseUrl = request.getRequestURI();
        return PaginationUtil.paginate(catalogDTOs, limit, offset, baseUrl);
    }

    @Override
    public CatalogDTO mapToDto(Catalog catalog) {
        return CatalogDTO.builder()
                .id(catalog.getId())
                .createdAt(catalog.getCreatedAt())
                .updatedAt(catalog.getUpdatedAt())
                .name(catalog.getName())
                .nameRu(catalog.getNameRu())
                .nameEn(catalog.getNameEn())
                .nameUz(catalog.getNameUz())
                .nameCy(catalog.getNameCy())
                .banner(catalog.getBanner())
                .children(getChildren(catalog.getId()))
                .build();
    }

    private List<CatalogDTO> getChildren(Integer parentId) {
        List<Catalog> children = catalogRepository.findByParentId(parentId);
        return children.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
