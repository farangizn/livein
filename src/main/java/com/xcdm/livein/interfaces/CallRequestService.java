package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.CallRequest;
import org.springframework.stereotype.Service;

@Service
public interface CallRequestService {
    CallRequest save(CallRequest callRequest);
}
