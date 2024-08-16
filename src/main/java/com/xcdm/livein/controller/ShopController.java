package com.xcdm.livein.controller;

import com.xcdm.livein.dto.*;
import com.xcdm.livein.entity.*;
import com.xcdm.livein.interfaces.*;
import com.xcdm.livein.pagination.PaginatedResponse;
import com.xcdm.livein.pagination.PaginationUtil;
import com.xcdm.livein.repo.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final DeliveryPointRepository deliveryPointRepository;
    private final SiteReviewRepository siteReviewRepository;
    private final CallRequestService callRequestService;
    private final MaterialRepository materialRepository;
    private final ColorRepository colorRepository;
    private final ShowroomService showroomService;
    private final ProductService productService;
    private final CatalogService catalogService;
    private final RoomRepository roomRepository;
    private final ReviewService reviewService;
    private final FaqRepository faqRepository;
    private final ShopService shopService;
    private final TextureRepository textureRepository;

    @PostMapping("call-request")
    public HttpEntity<?> saveCallRequest(@RequestBody CallRequestCreateDTO callRequestDTO) {
        return callRequestService.saveCallRequest(callRequestDTO);
    }

    @GetMapping("/catalogs")
    public ResponseEntity<PaginatedResponse<CatalogDTO>> getCatalog(
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(catalogService.formCatalogsResult(limit, offset, request));
    }

    @GetMapping("/faq")
    public HttpEntity<PaginatedResponse<Faq>> getFaq(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                                        HttpServletRequest request) {
        String baseUrl = request.getRequestURI();
        PaginatedResponse<Faq> response = PaginationUtil.paginate(faqRepository.findAll(), limit, offset, baseUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/colors")
    public HttpEntity<List<Color>> getColors() {
        return ResponseEntity.ok(colorRepository.findAll());
    }

    @GetMapping("/delivery-points")
    public HttpEntity<PaginatedResponse<DeliveryPoint>> getDeliveryPoints(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            HttpServletRequest request) {
        String baseUrl = request.getRequestURI();
        PaginatedResponse<DeliveryPoint> response = PaginationUtil.paginate(deliveryPointRepository.findAll(), limit, offset, baseUrl);
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
    public HttpEntity<PaginatedResponse<ReviewDTO>> getProductReviews(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                                      @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                      HttpServletRequest request) {
        return reviewService.formProductReview(limit, offset, request);
    }

    @PostMapping("/product-review")
    public HttpEntity<ReviewDTO> saveProductReview(@RequestBody @Valid ReviewCreateDTO reviewCreateDTO) {
        return reviewService.saveProductReview(reviewCreateDTO);
    }

    @GetMapping("/product-review/{product_id}")
    public HttpEntity<List<ReviewDTO>> getProductReview(@PathVariable Integer product_id) {
        return reviewService.getReviewByProductId(product_id);
    }

    @GetMapping("/products")
    public HttpEntity<PaginatedResponse<Product>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "min_price", required = false) String minPrice,
            @RequestParam(value = "max_price", required = false) String maxPrice,
            @RequestParam(value = "catalog", required = false) String catalog,
            @RequestParam(value = "room", required = false) String room,
            @RequestParam(value = "colors_hex_code", required = false) String colorsHexCode,
            @RequestParam(value = "material", required = false) String material,
            @RequestParam(value = "shop", required = false) String shop,
            @RequestParam(value = "random", required = false) String random,
            @RequestParam(value = "order_by", required = false) String orderBy,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            HttpServletRequest request
    ) {
        String baseUrl = request.getRequestURI();
        List<Product> products = productService.findProducts(name, price, minPrice, maxPrice, catalog, room, colorsHexCode, material, shop, random, orderBy, limit, offset);
        PaginatedResponse<Product> response = PaginationUtil.paginate(products, limit, offset, baseUrl);
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
    public HttpEntity<PaginatedResponse<ShopDTO>> getShop(
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 HttpServletRequest request
    ) {
        return shopService.getShopsResponse(limit, offset, request);
    }

    @GetMapping("/showrooms")
    public HttpEntity<PaginatedResponse<ShowroomDTO>> getShowRooms(
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            HttpServletRequest request
    ) {
        return showroomService.getShowroomsResponse(limit, offset, position, request);
    }

//    @PostMapping(value = "/showrooms", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public HttpEntity<ShowroomDTO> saveShowroom(
//                                    @RequestParam("banner") MultipartFile banner,
//                                    @RequestParam(value = "position", required = false) String position,
//                                    @RequestParam("shop") Integer shopId) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(showroomService.createAndSave(banner, position, shopId));
//    }

    @PostMapping(value = "/showrooms", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ShowroomDTO> createShowroom(
            @RequestParam("banner") MultipartFile banner,
            @RequestParam("shop") Integer shopId,
            @RequestParam("position") String position) {
            ShowroomDTO showroomModel = showroomService.createShowroom(banner, shopId, position);
            return new ResponseEntity<>(showroomModel, HttpStatus.CREATED);

    }

    @GetMapping("/showrooms/{id}")
    public HttpEntity<?> getShowRoomsByShowroomId(@PathVariable Integer id) {
        return showroomService.findShowroomById(id);
    }

    @GetMapping("site-review")
    public HttpEntity<PaginatedResponse<SiteReview>> getSiteReviews(@RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                                                       @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
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
    public HttpEntity<?> getShowroomProducts(@PathVariable Integer showroom_id,
                                                                       @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                                                       @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
                                                                       HttpServletRequest request) {
        return showroomService.findShowroomProductsByShowroomId(showroom_id, limit, offset, request);
    }

    @PostMapping("/{showroom_id}/showroom-products")
    public HttpEntity<?> saveShowroomProduct(@RequestBody ShowroomReadDTO showroomReadDTO, @PathVariable Integer showroom_id) {
        return showroomService.saveShowroomProducts(showroomReadDTO, showroom_id);
    }

    @PostMapping("/image-create")
    public HttpEntity<Texture> createImage(@RequestParam String text) {
        Texture texture = Texture.builder().image(text).build();
        Texture save = textureRepository.save(texture);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }


}
