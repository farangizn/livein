package com.xcdm.livein.service;

import lombok.RequiredArgsConstructor;
import com.xcdm.livein.entity.Application;
import com.xcdm.livein.interfaces.ApplicationService;
import com.xcdm.livein.repo.ApplicationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }
}
