package com.xcdm.livein.service;

import com.xcdm.livein.dto.ShopDTO;
import com.xcdm.livein.entity.Shop;
import com.xcdm.livein.mappers.ShopMapper;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.ShopRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.interfaces.ShopService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public Optional<Shop> findById(Integer shopId) {
        return shopRepository.findById(shopId);
    }

    @Override
    public HttpEntity<PaginatedResponse<ShopDTO>> getShopsResponse(int limit, int offset, HttpServletRequest request) {
        String baseUrl = request.getRequestURI();

        List<ShopDTO> shopDTOS = shopRepository.findAll()
                .stream()
                .map(shop ->{
                    ShopDTO dto = shopMapper.toDto(shop);
                    dto.setOwnerId(shop.getOwner().getId());
                    return dto;
                })
                .toList();

        PaginatedResponse<ShopDTO> response = PaginationUtil.paginate(shopDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }
}
