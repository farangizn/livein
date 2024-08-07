package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Showroom;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface ShowroomService {
    Showroom createAndSave(MultipartFile banner, String position, Integer shopId);

    Optional<Showroom> findById(Integer id);

    List<Showroom> findAllByPosition(String position);
}
