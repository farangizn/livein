package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Shop;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShopService {
    Optional<Shop> findById(Integer shopId);
}
