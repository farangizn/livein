package com.xcdm.livein.service;

import com.xcdm.livein.entity.Shop;
import com.xcdm.livein.entity.Showroom;
import com.xcdm.livein.repo.ShowroomRepository;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.interfaces.ShopService;
import com.xcdm.livein.interfaces.ShowroomService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowroomServiceImpl implements ShowroomService {
    private final ShopService shopService;
    private final ShowroomRepository showroomRepository;

    @Override
    public Showroom createAndSave(MultipartFile banner, String position, Integer shopId) {
        Optional<Shop> shopOptional = shopService.findById(shopId);

        Showroom showroom = Showroom.builder()
                .shop(shopOptional.orElseThrow(() -> new RuntimeException("shop not found")))
                .position(position)
//                .banner(banner.)
                .build();

        return showroomRepository.save(showroom);
    }

    @Override
    public Optional<Showroom> findById(Integer id) {
        return showroomRepository.findById(id);
    }

    @Override
    public List<Showroom> findAllByPosition(String position) {
        return showroomRepository.findByPosition(position);
    }
}
