package com.xcdm.livein.service;

import com.xcdm.livein.entity.Shop;
import com.xcdm.livein.repo.ShopRepository;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.interfaces.ShopService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;

    @Override
    public Optional<Shop> findById(Integer shopId) {
        return shopRepository.findById(shopId);
    }
}
