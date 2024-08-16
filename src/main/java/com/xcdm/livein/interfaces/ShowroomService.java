package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.ShowroomDTO;
import com.xcdm.livein.dto.ShowroomReadDTO;
import com.xcdm.livein.entity.Showroom;
import com.xcdm.livein.pagination.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface ShowroomService {
    ShowroomDTO createAndSave(MultipartFile banner, String position, Integer shopId);

    Optional<Showroom> findById(Integer id);

    List<Showroom> findAllByPosition(String position);

    HttpEntity<PaginatedResponse<ShowroomDTO>> getShowroomsResponse(int limit, int offset, String position, HttpServletRequest request);

    HttpEntity<?> findShowroomById(Integer id);

    HttpEntity<?> findShowroomProductsByShowroomId(Integer showroomId, int limit, int offset, HttpServletRequest request);

    HttpEntity<?> saveShowroomProducts(ShowroomReadDTO showroomInnerDTOS, Integer showroomId);


    ShowroomDTO createShowroom(MultipartFile banner, Integer shopId, String position);
}
