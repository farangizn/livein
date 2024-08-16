package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.ApplicationDTO;
import com.xcdm.livein.entity.Application;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface ApplicationService {
    Application saveApplication(ApplicationDTO applicationDTO);
}
