package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.CallRequestCreateDTO;
import com.xcdm.livein.entity.CallRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface CallRequestService {
    CallRequest save(CallRequest callRequest);

    HttpEntity<?> saveCallRequest(CallRequestCreateDTO callRequestDTO);
}
