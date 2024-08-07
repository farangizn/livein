package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Application;
import org.springframework.stereotype.Service;

@Service
public interface ApplicationService {
    Application save(Application application);
}
