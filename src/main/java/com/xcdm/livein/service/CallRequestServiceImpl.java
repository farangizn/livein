package com.xcdm.livein.service;

import com.xcdm.livein.dto.CallRequestCreateDTO;
import com.xcdm.livein.dto.CallRequestReadDTO;
import com.xcdm.livein.entity.CallRequest;
import com.xcdm.livein.entity.Product;
import com.xcdm.livein.interfaces.CallRequestService;
import com.xcdm.livein.mappers.CallRequestMapper;
import com.xcdm.livein.mappers.CallRequestReadMapper;
import com.xcdm.livein.repo.CallRequestRepository;
import com.xcdm.livein.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallRequestServiceImpl implements CallRequestService {

    private final CallRequestRepository callRequestRepository;
    private final CallRequestMapper callRequestMapper;
    private final ProductRepository productRepository;
    private final CallRequestReadMapper callRequestReadMapper;


    @Override
    public CallRequest save(CallRequest callRequest) {
        return callRequestRepository.save(callRequest);
    }

    @Override
    public HttpEntity<?> saveCallRequest(CallRequestCreateDTO callRequestDTO) {
        CallRequest callRequest = callRequestMapper.toEntity(callRequestDTO);
        Optional<Product> productOptional = productRepository.findById(callRequestDTO.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            callRequest.setProduct(product);
            CallRequestReadDTO dto = callRequestReadMapper.toDto(save(callRequest));
            dto.setProductId(product.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.badRequest().body("Product under id " + callRequestDTO.getProductId() + " not found");
        }
    }
}
