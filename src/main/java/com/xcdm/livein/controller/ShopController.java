package com.xcdm.livein.controller;

import com.xcdm.livein.dto.*;
import com.xcdm.livein.entity.*;
import com.xcdm.livein.interfaces.CallRequestService;
import com.xcdm.livein.interfaces.ReviewService;
import com.xcdm.livein.mappers.*;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.interfaces.ShowroomService;
import com.xcdm.livein.interfaces.SiteReviewService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final CatalogRepository catalogRepository;
    private final ColorRepository colorRepository;
    private final DeliveryPointRepository deliveryPointRepository;
    private final FaqRepository faqRepository;
    private final MaterialRepository materialRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final RoomRepository roomRepository;
    private final ShowroomRepository showroomRepository;
    private final ShowroomService showroomService;
    private final SiteReviewRepository siteReviewRepository;
    private final SiteReviewMapper siteReviewMapper;
    private final CallRequestService callRequestService;
    private final CallRequestMapper callRequestMapper;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final WishListMapper wishListMapper;
    private final ShowroomMapper showroomMapper;
    private final ShopShowroomProductRepository shopShowroomProductRepository;

    @PostMapping("call-request")
    public HttpEntity<CallRequest> saveCallRequest(@RequestBody CallRequestDTO callRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callRequestService.save(callRequestMapper.toEntity(callRequestDTO)));
    }

    @GetMapping("/catalogs")
    public HttpEntity<PaginatedResponse<Catalog>> getCatalogs(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 HttpServletRequest request) {

        String baseUrl = request.getRequestURI();

        PaginatedResponse<Catalog> response = PaginationUtil.paginate(catalogRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/colors")
    public HttpEntity<List<Color>> getColors() {
        return ResponseEntity.ok(colorRepository.findAll());
    }

    @GetMapping("/delivery-points")
    public HttpEntity<PaginatedResponse<DeliveryPoint>> getDeliveryPoints(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                             HttpServletRequest request) {
        String baseUrl = request.getRequestURI();

        PaginatedResponse<DeliveryPoint> response = PaginationUtil.paginate(deliveryPointRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/faq")
    public HttpEntity<PaginatedResponse<Faq>> getFaq(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                                        HttpServletRequest request) {


        String baseUrl = request.getRequestURI();

        PaginatedResponse<Faq> response = PaginationUtil.paginate(faqRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/materials")
    public HttpEntity<PaginatedResponse<Material>> getMaterials(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                   @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                   HttpServletRequest request) {
        String baseUrl = request.getRequestURI();

        PaginatedResponse<Material> response = PaginationUtil.paginate(materialRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product-review")
    public HttpEntity<PaginatedResponse<Review>> getProductReviews(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                      @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                      HttpServletRequest request) {
        String baseUrl = request.getRequestURI();

        PaginatedResponse<Review> response = PaginationUtil.paginate(reviewRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/product-review")
    public HttpEntity<Review> saveProductReview(@RequestBody ReviewDTO reviewDTO) {
       return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.save(reviewMapper.toEntity(reviewDTO)));
    }

    @GetMapping("/product-review/{product_id}")
    public HttpEntity<Review> getProductReview(@PathVariable Integer product_id) {
        Optional<Review> reviewOptional = reviewService.findByProductId(product_id);
        return reviewOptional.<HttpEntity<Review>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/products")
    public HttpEntity<PaginatedResponse<Product>> getProducts(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            HttpServletRequest request
    ) {
        String baseUrl = request.getRequestURI();

        PaginatedResponse<Product> response = PaginationUtil.paginate(productRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/rooms")
    public HttpEntity<PaginatedResponse<Room>> getRooms(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                           @RequestParam(value = "offset", defaultValue = "0") int offset,
                                           HttpServletRequest request
    ) {
        String baseUrl = request.getRequestURI();

        PaginatedResponse<Room> response = PaginationUtil.paginate(roomRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/shop")
    public HttpEntity<PaginatedResponse<Shop>> getShop(
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 HttpServletRequest request
    ) {
        String baseUrl = request.getRequestURI();

        PaginatedResponse<Shop> response = PaginationUtil.paginate(shopRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/showrooms")
    public HttpEntity<PaginatedResponse<ShowroomDTO>> getShowRooms(
            @RequestParam(value = "position") String position,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            HttpServletRequest request
    ) {
        String baseUrl = request.getRequestURI();

        List<ShowroomDTO> showroomDTOS = showroomService.findAllByPosition(position)
                .stream()
                .map(showroomMapper::toDto)
                .toList();

        PaginatedResponse<ShowroomDTO> response = PaginationUtil.paginate(showroomDTOS, limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @PostMapping ("/showrooms") // where do i save the banner?
    public HttpEntity<?> saveShowroom(@RequestParam("banner") MultipartFile banner,
                                      @RequestParam(value = "position", required = false) String position,
                                      @RequestParam("shop") Integer shopId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showroomService.createAndSave(banner, position, shopId));
    }

    @GetMapping("/showrooms/{id}")
    public HttpEntity<ShowroomDTO> getShowRooms(@PathVariable(required = true) Integer id) {
        Optional<Showroom> showroomOptional = showroomService.findById(id);
        if (showroomOptional.isPresent()) {
            ShowroomDTO showroomDTO = showroomMapper.toDto(showroomOptional.get());
            return ResponseEntity.ok(showroomDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("site-review")
    public HttpEntity<PaginatedResponse<SiteReview>> getSiteReviews(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                       HttpServletRequest request) {

        String baseUrl = request.getRequestURI();

        PaginatedResponse<SiteReview> response = PaginationUtil.paginate(siteReviewRepository.findAll(), limit, offset, baseUrl);

        return ResponseEntity.ok(response);
    }

    @PostMapping("site-review")
    public HttpEntity<SiteReview> saveSiteReview(@RequestBody SiteReviewDTO siteReviewDTO) {

        SiteReview siteReview = SiteReview.builder().name(siteReviewDTO.getName()).text(siteReviewDTO.getText()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(siteReviewRepository.save(siteReview));
    }

    @GetMapping("/{showroom_id}/showroom-products")
    public HttpEntity<PaginatedResponse<ShopShowroomProduct>> getShops(@PathVariable Integer showroom_id,
                                                                       @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                       HttpServletRequest request) {
        Optional<Showroom> showroomOptional = showroomService.findById(showroom_id);

        if (showroomOptional.isPresent()) {
            Showroom showroom = showroomOptional.get();
            List<ShopShowroomProduct> shopShowroomProducts = shopShowroomProductRepository.findByShowroom(showroom);
            String baseUrl = request.getRequestURI();

            PaginatedResponse<ShopShowroomProduct> response = PaginationUtil.paginate(shopShowroomProducts, limit, offset, baseUrl);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
