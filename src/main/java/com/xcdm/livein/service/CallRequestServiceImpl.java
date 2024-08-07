package com.xcdm.livein.service;

import com.xcdm.livein.entity.CallRequest;
import com.xcdm.livein.interfaces.CallRequestService;
import com.xcdm.livein.repo.CallRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallRequestServiceImpl implements CallRequestService {

    private final CallRequestRepository callRequestRepository;

    @Override
    public CallRequest save(CallRequest callRequest) {
        return callRequestRepository.save(callRequest);
    }
}
