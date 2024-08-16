package com.xcdm.livein.service;

import com.xcdm.livein.dto.*;
import com.xcdm.livein.entity.*;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.ProductRepository;
import com.xcdm.livein.repo.ShopRepository;
import com.xcdm.livein.repo.ShopShowroomProductRepository;
import com.xcdm.livein.repo.ShowroomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.interfaces.ShopService;
import com.xcdm.livein.interfaces.ShowroomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowroomServiceImpl implements ShowroomService {
    private final ShopService shopService;
    private final ShowroomRepository showroomRepository;
    private final ProductRepository productRepository;
    private final ShopShowroomProductRepository shopShowroomProductRepository;
    private final ShowroomProductRepository showroomProductRepository;
    private final ShopRepository shopRepository;
    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public ShowroomDTO createAndSave(MultipartFile banner, String position, Integer shopId) {
        Optional<Shop> shopOptional = shopService.findById(shopId);

        String filename = UUID.randomUUID() + "_" + banner.getOriginalFilename();

        String filePath = uploadDir + filename;

        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Files.copy(banner.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store banner file", e);
        }

        Showroom showroom = showroomRepository.save(Showroom.builder()
                .shop(shopOptional.orElseThrow(() -> new RuntimeException("shop not found")))
                .position(position)
                .banner(filePath)
                .build());

        return ShowroomDTO.builder()
                .id(showroom.getId())
                .position(position)
                .banner(filePath)
                .products(showroom.getProducts().stream().map(Product::getName).toList())
                .build();
    }


    @Override
    public Optional<Showroom> findById(Integer id) {
        return showroomRepository.findById(id);
    }

    @Override
    public List<Showroom> findAllByPosition(String position) {
        return showroomRepository.findByPosition(position);
    }

    @Override
    public HttpEntity<PaginatedResponse<ShowroomDTO>> getShowroomsResponse(int limit, int offset, String position, HttpServletRequest request) {
        String baseUrl = request.getRequestURI();

        List<Showroom> showrooms;

        if (position != null) {
            showrooms = findAllByPosition(position);
        } else {
            showrooms = showroomRepository.findAll();
        }

        List<ShowroomDTO> showroomDTOS = new ArrayList<>();
        for (Showroom showroom : showrooms) {
            ShowroomDTO showroomDTO = ShowroomDTO.builder()
                    .id(showroom.getId())
                    .shopId(showroom.getShop().getId())
                    .position(showroom.getPosition())
                    .banner(showroom.getBanner())
                    .products(showroom.getProducts().stream().map(Product::getName).toList())
                    .build();
            showroomDTOS.add(showroomDTO);
        }

        PaginatedResponse<ShowroomDTO> response = PaginationUtil.paginate(showroomDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> findShowroomById(Integer id) {
        Optional<Showroom> showroomOptional = findById(id);
        if (showroomOptional.isPresent()) {
            Showroom showroom = showroomOptional.get();
            ShowroomDTO showroomDTO = ShowroomDTO.builder()
                    .id(showroom.getId())
                    .shopId(showroom.getShop().getId())
                    .products(showroom.getProducts().stream().map(Product::getName).toList())
                    .banner(showroom.getBanner())
                    .position(showroom.getPosition())
                    .build();
            return ResponseEntity.ok(showroomDTO);
        } else {
            return ResponseEntity.badRequest().body("Showroom under id " + id + " not found");
        }
    }

    @Override
    public HttpEntity<?> findShowroomProductsByShowroomId(Integer showroomId, int limit, int offset, HttpServletRequest request) {
        Optional<Showroom> showroomOptional = findById(showroomId);

        if (showroomOptional.isPresent()) {
            List<ShowroomProduct> showroomProducts = showroomProductRepository.findAllByShowroomId(showroomId);

            List<ShopShowroomproduct> shopShowroomproducts = new ArrayList<>();
            for (ShowroomProduct product : showroomProducts) {
                List<ShopShowroomproduct> ssps = shopShowroomProductRepository.findAllByShowroomProduct(product);
                shopShowroomproducts.addAll(ssps);
            }

            String baseUrl = request.getRequestURI();

            List<ShowroomInnerPaginatedDTO> showroomInnerDTOS = new ArrayList<>();

            for (ShopShowroomproduct product : shopShowroomproducts) {
                showroomInnerDTOS.add(new ShowroomInnerPaginatedDTO(product.getId(), product.getX(), product.getY(), product.getShowroomProduct().getId()));
            }
            PaginatedResponse<ShowroomInnerPaginatedDTO> response = PaginationUtil.paginate(showroomInnerDTOS, limit, offset, baseUrl);
            ShowroomPaginatedDTO showroomPaginatedDTO = ShowroomPaginatedDTO.builder().showroomProducts(response).build();

            return ResponseEntity.ok(showroomPaginatedDTO);
        } else {
            return ResponseEntity.badRequest().body("Showroom under id " + showroomId + " is not present");
        }
    }

    @Override
    public HttpEntity<?> saveShowroomProducts(ShowroomReadDTO showroomReadDTO, Integer showroomId) {


        Optional<Showroom> showroomOptional = showroomRepository.findById(showroomId);

        if (showroomOptional.isPresent()) {
            List<ShowroomInnerDTO> showroomProducts = showroomReadDTO.getShowroomProducts();

            List<ShowroomInnerPaginatedDTO> showroomInnerDTOS = new ArrayList<>();
            for (ShowroomInnerDTO showroomInnerDTO : showroomProducts) {
                Integer showroomProductId = showroomInnerDTO.getProductId();

                Optional<ShowroomProduct> showroomProductOptional = showroomProductRepository.findById(showroomProductId);
                if (showroomProductOptional.isEmpty()) {
                    return ResponseEntity.badRequest().body("Showroom product with id " + showroomInnerDTO.getProductId() + " does not exist");
                }
                ShowroomProduct showroomProduct = showroomProductOptional.get();

                ShopShowroomproduct shopShowroomproduct = ShopShowroomproduct.builder()
                        .showroomProduct(showroomProduct)
                        .x(showroomInnerDTO.getX())
                        .y(showroomInnerDTO.getY())
                        .build();

                showroomInnerDTOS.add(ShowroomInnerPaginatedDTO.builder()
                        .productId(shopShowroomproduct.getShowroomProduct().getId())
                        .x(shopShowroomproduct.getX())
                        .y(shopShowroomproduct.getY())
                        .build());

                shopShowroomProductRepository.save(shopShowroomproduct);
            }

            ShowroomOuterDTO showroomOuterDTO = ShowroomOuterDTO.builder().products(showroomInnerDTOS).build();

            return ResponseEntity.ok(showroomOuterDTO);
        } else {
            return ResponseEntity.badRequest().body("Showroom under id " + showroomId + " is not present");
        }
    }

    @Override
    public ShowroomDTO createShowroom(MultipartFile bannerFile, Integer shopId, String position) {
        Showroom showroom = new Showroom();
        if (bannerFile != null && !bannerFile.isEmpty()) {
            String bannerPath = saveBannerFile(bannerFile);
            showroom.setBanner(bannerPath);
        }

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found"));
        showroom.setShop(shop);

        showroom.setPosition(position);
        showroom = showroomRepository.save(showroom);

        return convertToShowroomModel(showroom);
    }

    private String saveBannerFile(MultipartFile bannerFile) {
        String filename = UUID.randomUUID() + "_" + bannerFile.getOriginalFilename();

        String filePath = uploadDir + filename;

        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Files.copy(bannerFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store banner file", e);
        }

        return filePath;
    }

    private ShowroomDTO convertToShowroomModel(Showroom showroom) {
        ShowroomDTO dto = ShowroomDTO.builder()
                .id(showroom.getId())
                .banner(showroom.getBanner())
                .position(showroom.getPosition())
                .shopId(showroom.getShop().getId())
                .build();
        if (showroom.getProducts() != null) {
            List<String> productNames = showroom.getProducts().stream()
                    .map(Product::getName) // Assuming Product entity has a getName() method
                    .toList();
            dto.setProducts(productNames);
        }


        return dto;
    }
}
