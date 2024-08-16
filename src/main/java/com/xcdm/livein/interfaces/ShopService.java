package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.ShopDTO;
import com.xcdm.livein.entity.Shop;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShopService {
    Optional<Shop> findById(Integer shopId);

    HttpEntity<PaginatedResponse<ShopDTO>> getShopsResponse(int limit, int offset, HttpServletRequest request);
}
