package com.xcdm.livein.service;

import com.xcdm.livein.dto.ApplicationDTO;
import com.xcdm.livein.entity.Catalog;
import com.xcdm.livein.entity.District;
import com.xcdm.livein.entity.Region;
import com.xcdm.livein.repo.CatalogRepository;
import com.xcdm.livein.repo.DistrictRepository;
import com.xcdm.livein.repo.RegionRepository;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.entity.Application;
import com.xcdm.livein.interfaces.ApplicationService;
import com.xcdm.livein.repo.ApplicationRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final DistrictRepository districtRepository;
    private final CatalogRepository catalogRepository;
    private final RegionRepository regionRepository;

    @Override
    public Application saveApplication(ApplicationDTO applicationDTO) {

        Region region = regionRepository.findById(applicationDTO.getRegionId())
                .orElseThrow(() -> new RuntimeException("Region not found"));
        District district = districtRepository.findById(applicationDTO.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(applicationDTO.getDate());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format, please use 'yyyy-MM-dd'");
        }

        Application application = Application.builder()
                .firstName(applicationDTO.getFirstName())
                .lastName(applicationDTO.getLastName())
                .phone(applicationDTO.getPhone())
                .address(applicationDTO.getAddress())
                .date(parsedDate)
                .timeRange(applicationDTO.getTimeRange())
                .region(region)
                .district(district)
                .build();

        if (applicationDTO.getCatalogIds() != null && !applicationDTO.getCatalogIds().isEmpty()) {
            List<Catalog> catalogs = catalogRepository.findAllById(applicationDTO.getCatalogIds());
            application.setCatalogs(catalogs);
        }
        if (applicationDTO.getComment() != null && !applicationDTO.getComment().isEmpty()) {
            application.setComment(applicationDTO.getComment());
        }

        return applicationRepository.save(application);
    }
}
