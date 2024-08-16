package com.xcdm.livein.component;

import com.xcdm.livein.entity.*;
import com.xcdm.livein.enums.*;
import com.xcdm.livein.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final CatalogRepository catalogRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final CallRequestRepository callRequestRepository;
    private final CardRepository cardRepository;
    private final ColorRepository colorRepository;

    public static List<Status> statuses = Arrays.asList(Status.NEW, Status.FINISHED, Status.IN_PROGRESS);
    public static List<DeliveryType> deliveryTypes = Arrays.asList(DeliveryType.A, DeliveryType.B);
    public static List<String> discountTypes = Arrays.asList("max", "basic", "non-basic", "premium");

    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ShopRepository shopRepository;
    private final DeliveryPointRepository deliveryPointRepository;
    private final FaqRepository faqRepository;
    private final SiteReviewRepository siteReviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductImageRepository productImageRepository;
    private final ShowroomRepository showroomRepository;
    private final ShopShowroomProductRepository shopShowroomProductRepository;
    private final WishListRepository wishListRepository;
    private final UpholsterRepository upholsterRepository;
    private final CartItemRepository cartItemRepository;


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    private final RoomRepository roomRepository;
    private final DiscountRepository discountRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final PaymentRepository paymentRepository;
    private final ShowroomProductRepository showroomProductRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (ddl.equals("create")) {

            List<Role> roles = generateRoles();
            List<Role> savedRoles = roleRepository.saveAll(roles);

            generateUser(savedRoles);

            List<User> users = generateUsers();
            userRepository.saveAll(users);

            List<Catalog> catalogs = generateCatalogs();
            catalogRepository.saveAll(catalogs);

            generateChildCatalog();

            List<Card> cards = generateCards(users);
            cardRepository.saveAll(cards);

            List<Upholster> upholsters = generateUpholsters();
            upholsterRepository.saveAll(upholsters);

            List<Discount> discounts = generateDiscounts();
            discountRepository.saveAll(discounts);

            List<Cart> carts = generateCarts(users);
            cartRepository.saveAll(carts);

            List<Color> colors = generateColors();
            colorRepository.saveAll(colors);

            List<Region> regions = generateRegions();
            regionRepository.saveAll(regions);

            List<District> districts = generateDistricts(regions);
            districtRepository.saveAll(districts);

            List<Order> orders = generateOrders();
            orderRepository.saveAll(orders);

            List<Delivery> deliveries = generateDeliveries(orders);
            deliveryRepository.saveAll(deliveries);

            List<Shop> shops = generateShops();
            shopRepository.saveAll(shops);

            List<DeliveryPoint> deliveryPoints = generateDeliveryPoints(shops);
            deliveryPointRepository.saveAll(deliveryPoints);


            List<Faq> faqs = generateFaqs();
            faqRepository.saveAll(faqs);

            List<Material> materials = generateMaterials();
            materialRepository.saveAll(materials);

            List<SearchHistory> searchHistories = generateSearchHistories();
            searchHistoryRepository.saveAll(searchHistories);

            List<Room> rooms = generateRooms();
            roomRepository.saveAll(rooms);

            List<SiteReview> siteReviews = generateSiteReviews();
            siteReviewRepository.saveAll(siteReviews);

            List<Product> products = generateProducts();
            productRepository.saveAll(products);

            List<CallRequest> callRequests = generateCallRequests(products);
            callRequestRepository.saveAll(callRequests);

            List<OrderItem> orderItems = generateOrderItems(orders);
            orderItemRepository.saveAll(orderItems);

            List<Payment> payments = generatePayments();
            paymentRepository.saveAll(payments);

            List<Review> reviews = generateReviews();
            reviewRepository.saveAll(reviews);

            List<ProductImage> productImages = generateProductImages();
            productImageRepository.saveAll(productImages);

            List<Showroom> showrooms = generateShowrooms();
            showroomRepository.saveAll(showrooms);

            List<ShowroomProduct> showroomProducts = generateShowroomProducts();
            showroomProductRepository.saveAll(showroomProducts);

            List<ShopShowroomproduct> shopShowroomproducts = generateShopShowroomProducts();
            shopShowroomProductRepository.saveAll(shopShowroomproducts);

            List<WishList> wishLists = generateWishLists();
            wishListRepository.saveAll(wishLists);

            generateCartItemsForSuperUser(cartRepository.findByUser((User) userRepository.findByEmail("farangizxon2004@gmail.com")));

        }
    }

    private List<Role> generateRoles() {
        return List.of(
                Role.builder().name(RoleName.ROLE_ADMIN).build(),
                Role.builder().name(RoleName.ROLE_USER).build(),
                Role.builder().name(RoleName.ROLE_SUPERUSER).build()
        );
    }

    private void generateChildCatalog() {
        catalogRepository.save(Catalog.builder().name("Toys for girls").nameEn("Toys for girls").nameUz("QIzlar uchun o'yinchoqlar").nameRu("Девчачьи игрушки").parentId(5).build());
    }

    private void generateUser(List<Role> roles) {
        User user = User.builder()
                .firstName("Farangiz")
                .lastName("Nasriddinova")
                .email("farangizxon2004@gmail.com")
                .password(passwordEncoder.encode("1"))
                .isSuperUser(true)
                .isStaff(true)
                .isActive(true)
                .phone("+998901234567")
                .accountType(AccountType.app)
                .dateJoined(ZonedDateTime.now())
                .roles(roles)
                .build();
        userRepository.save(user);

        Cart cart = Cart.builder().isOrdered(false).user(user).build();
        cartRepository.save(cart);
    }

    private void generateCartItemsForSuperUser(Cart cart) {
        CartItem cartItem1 = CartItem.builder()
                .cart(cart)
                .quantity(12)
                .product(productRepository.findAll().get(0))
                .build();

        CartItem cartItem2 = CartItem.builder()
                .cart(cart)
                .quantity(43)
                .product(productRepository.findAll().get(3))
                .build();

        CartItem cartItem3 = CartItem.builder()
                .cart(cart)
                .quantity(2)
                .product(productRepository.findAll().get(2))
                .build();

        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);
        cartItemRepository.save(cartItem3);
    }

    private static List<Catalog> generateCatalogs() {
        return Arrays.asList(
                Catalog.builder().name("Furniture").nameEn("Furniture").nameRu("Мебель").nameUz("Mebel").nameCy("家具").banner("furniture_banner.jpg").createdAt(ZonedDateTime.now()).build(),
                Catalog.builder().name("Electronics").nameEn("Electronics").nameRu("Электроника").nameUz("Elektronika").nameCy("电子产品").banner("electronics_banner.jpg").build(),
                Catalog.builder().name("Clothing").nameEn("Clothing").nameRu("Одежда").nameUz("Kiyim").nameCy("服装").banner("clothing_banner.jpg").build(),
                Catalog.builder().name("Books").nameEn("Books").nameRu("Книги").nameUz("Kitoblar").nameCy("书籍").banner("books_banner.jpg").build(),
                Catalog.builder().name("Toys").nameEn("Toys").nameRu("Игрушки").nameUz("Oʻyinchoqlar").nameCy("玩具").banner("toys_banner.jpg").build()
        );
    }

    private List<Product> generateProducts() {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<Material> materials = materialRepository.findAll();
        List<Color> colors = colorRepository.findAll();
        List<Discount> discounts = discountRepository.findAll();
        return Arrays.asList(
                Product.builder()
                        .name("Ultra HD TV")
                        .price(799.99)
                        .description("4K Ultra HD Smart TV with excellent picture quality.")
                        .quantity(BigInteger.valueOf(50))
                        .catalog(catalogs.get(0))
                        .material(materials.get(0))
                        .room(rooms.get(0))
                        .isActive(true)
                        .isFavorite(false)
                        .madeIn("USA")
                        .descriptionEn("4K Ultra HD Smart TV with excellent picture quality.")
                        .descriptionUz("4K Ultra HD Smart TV ajoyib tasvir sifati bilan.")
                        .descriptionRu("4K Ultra HD Smart TV с отличным качеством изображения.")
                        .descriptionCy("Teledu Smart 4K Ultra HD gyda chwaith delwedd rhagorol.")
                        .nameEn("Ultra HD TV")
                        .nameUz("Ultra HD TV")
                        .nameRu("Ультра HD ТВ")
                        .nameCy("Teledu HD Ultra")
                        .discounts(Arrays.asList(discounts.get(0)))
                        .colors(Arrays.asList(colors.get(0)))
                        .build(),
                Product.builder()
                        .name("Bluetooth Speaker")
                        .price(129.99)
                        .description("Portable Bluetooth Speaker with powerful sound and long battery life.")
                        .quantity(BigInteger.valueOf(150))
                        .catalog(catalogs.get(1))
                        .material(materials.get(1))
                        .room(rooms.get(1))
                        .isActive(true)
                        .isFavorite(true)
                        .madeIn("China")
                        .descriptionEn("Portable Bluetooth Speaker with powerful sound and long battery life.")
                        .descriptionUz("Kuchli ovoz va uzoq vaqt ishlashga ega portativ Bluetooth dinamik.")
                        .descriptionRu("Портативная Bluetooth-колонка с мощным звуком и длительным временем работы.")
                        .descriptionCy("Loudspeaker Bluetooth symudol gyda sain pwerus a bywyd batri hir.")
                        .nameEn("Bluetooth Speaker")
                        .nameUz("Bluetooth Speaker")
                        .nameRu("Bluetooth Колонка")
                        .nameCy("Siaradwr Bluetooth")
                        .discounts(Arrays.asList(discounts.get(1)))
                        .colors(Arrays.asList(colors.get(1)))
                        .build(),
                Product.builder()
                        .name("Coffee Maker")
                        .price(89.99)
                        .description("Drip Coffee Maker with programmable settings and auto-shutoff.")
                        .quantity(BigInteger.valueOf(100))
                        .catalog(catalogs.get(2))
                        .material(materials.get(2))
                        .room(rooms.get(2))
                        .isActive(true)
                        .isFavorite(false)
                        .madeIn("Germany")
                        .descriptionEn("Drip Coffee Maker with programmable settings and auto-shutoff.")
                        .descriptionUz("Dasturlash sozlamalari va avtomatik o'chirish bilan suv qahvayichi.")
                        .descriptionRu("Кофеварка с капельным способом приготовления, программируемыми настройками и автоматическим отключением.")
                        .descriptionCy("Gwneuthurwr Coffi Gorchymynnol gyda gosodiadau a chyflenwad awtomatig.")
                        .nameEn("Coffee Maker")
                        .nameUz("Coffee Maker")
                        .nameRu("Кофеварка")
                        .nameCy("Gwneuthurwr Coffi")
                        .discounts(Arrays.asList(discounts.get(2)))
                        .colors(Arrays.asList(colors.get(2)))
                        .build(),
                Product.builder()
                        .name("Gaming Laptop")
                        .price(1499.99)
                        .description("High-performance gaming laptop with latest specs and cooling system.")
                        .quantity(BigInteger.valueOf(75))
                        .catalog(catalogs.get(3))
                        .material(materials.get(3))
                        .room(rooms.get(3))
                        .isActive(true)
                        .isFavorite(true)
                        .madeIn("South Korea")
                        .descriptionEn("High-performance gaming laptop with latest specs and cooling system.")
                        .descriptionUz("Yuqori samaradorlikka ega gaming noutbuki, so'nggi xususiyatlar va sovutish tizimi bilan.")
                        .descriptionRu("Игровой ноутбук высокой производительности с последними характеристиками и системой охлаждения.")
                        .descriptionCy("Laptop chwaraeon gyda perfformiad uchel, o'r radd flaenaf a system oeri.")
                        .nameEn("Gaming Laptop")
                        .nameUz("Gaming Laptop")
                        .nameRu("Игровой ноутбук")
                        .nameCy("Laptop Chwaraeon")
                        .discounts(Arrays.asList(discounts.get(3)))
                        .colors(Arrays.asList(colors.get(3)))
                        .build(),
                Product.builder()
                        .name("Smart Watch")
                        .price(199.99)
                        .description("Smart Watch with fitness tracking and notification features.")
                        .quantity(BigInteger.valueOf(200))
                        .catalog(catalogs.get(4))
                        .material(materials.get(4))
                        .room(rooms.get(4))
                        .isActive(true)
                        .isFavorite(true)
                        .madeIn("Japan")
                        .descriptionEn("Smart Watch with fitness tracking and notification features.")
                        .descriptionUz("Fitnesni kuzatish va bildirishnomalar xususiyatlariga ega aqlli soat.")
                        .descriptionRu("Умные часы с функциями отслеживания фитнеса и уведомления.")
                        .descriptionCy("Gloch Smart gyda chymhwysiadau olrhain ffitrwydd a nodiadau.")
                        .nameEn("Smart Watch")
                        .nameUz("Smart Watch")
                        .nameRu("Умные часы")
                        .nameCy("Gloch Smart")
                        .discounts(Arrays.asList(discounts.get(4)))
                        .colors(Arrays.asList(colors.get(4)))
                        .build(),
                // Add 15 more products in a similar fashion, ensuring all fields are included
                Product.builder()
                        .name("Electric Toothbrush")
                        .price(59.99)
                        .description("Electric Toothbrush with multiple brushing modes and timer.")
                        .quantity(BigInteger.valueOf(80))
                        .catalog(catalogs.get(0))
                        .material(materials.get(5))
                        .room(rooms.get(0))
                        .isActive(true)
                        .isFavorite(false)
                        .madeIn("Switzerland")
                        .descriptionEn("Electric Toothbrush with multiple brushing modes and timer.")
                        .descriptionUz("Bir nechta cho'tkalar rejimlari va taymer bilan elektr tish cho'tkasi.")
                        .descriptionRu("Электрическая зубная щетка с несколькими режимами и таймером.")
                        .descriptionCy("Bristlwyd Trydan gyda sawl dull brwsio a timer.")
                        .nameEn("Electric Toothbrush")
                        .nameUz("Electric Toothbrush")
                        .nameRu("Электрическая зубная щетка")
                        .nameCy("Bristlwyd Trydan")
                        .discounts(Arrays.asList(discounts.get(0)))
                        .colors(Arrays.asList(colors.get(5)))
                        .build(),
                Product.builder()
                        .name("Air Purifier")
                        .price(299.99)
                        .description("Air Purifier with HEPA filter and air quality monitoring.")
                        .quantity(BigInteger.valueOf(60))
                        .catalog(catalogs.get(1))
                        .material(materials.get(6))
                        .room(rooms.get(1))
                        .isActive(true)
                        .isFavorite(false)
                        .madeIn("Canada")
                        .descriptionEn("Air Purifier with HEPA filter and air quality monitoring.")
                        .descriptionUz("HEPA filtri va havo sifatini monitoring qilish bilan havo tozalagich.")
                        .descriptionRu("Очиститель воздуха с фильтром HEPA и мониторингом качества воздуха.")
                        .descriptionCy("Purifier Aer gyda ffilter HEPA a monitro ansawdd aer.")
                        .nameEn("Air Purifier")
                        .nameUz("Air Purifier")
                        .nameRu("Очиститель воздуха")
                        .nameCy("Purifier Aer")
                        .discounts(Arrays.asList(discounts.get(1)))
                        .colors(Arrays.asList(colors.get(6)))
                        .build(),
                Product.builder()
                        .name("Digital Camera")
                        .price(499.99)
                        .description("Digital Camera with high-resolution image capture and Wi-Fi connectivity.")
                        .quantity(BigInteger.valueOf(40))
                        .catalog(catalogs.get(2))
                        .material(materials.get(7))
                        .room(rooms.get(2))
                        .isActive(true)
                        .isFavorite(true)
                        .madeIn("Italy")
                        .descriptionEn("Digital Camera with high-resolution image capture and Wi-Fi connectivity.")
                        .descriptionUz("Yuqaridan sifatli tasvirlarni olish va Wi-Fi ulanish bilan raqamli kamera.")
                        .descriptionRu("Цифровая камера с высоким разрешением и Wi-Fi подключением.")
                        .descriptionCy("Camera Ddigidol gyda chymhwysedd delweddau uchel a chysylltedd Wi-Fi.")
                        .nameEn("Digital Camera")
                        .nameUz("Digital Camera")
                        .nameRu("Цифровая камера")
                        .nameCy("Camera Ddigidol")
                        .discounts(Arrays.asList(discounts.get(2)))
                        .colors(Arrays.asList(colors.get(7)))
                        .build(),
                Product.builder()
                        .name("Smartphone")
                        .price(699.99)
                        .description("Smartphone with advanced features and high-resolution display.")
                        .quantity(BigInteger.valueOf(100))
                        .catalog(catalogs.get(3))
                        .material(materials.get(8))
                        .room(rooms.get(3))
                        .isActive(true)
                        .isFavorite(true)
                        .madeIn("China")
                        .descriptionEn("Smartphone with advanced features and high-resolution display.")
                        .descriptionUz("Yuqori sifatli ekran va ilg'or xususiyatlarga ega smartfon.")
                        .descriptionRu("Смартфон с передовыми функциями и дисплеем высокого разрешения.")
                        .descriptionCy("Ffôn Clyfar gyda nodweddion uwch a chymhwysedd darlun uchel.")
                        .nameEn("Smartphone")
                        .nameUz("Smartphone")
                        .nameRu("Смартфон")
                        .nameCy("Ffôn Clyfar")
                        .discounts(Arrays.asList(discounts.get(3)))
                        .colors(Arrays.asList(colors.get(8)))
                        .build(),
                Product.builder()
                        .name("Microwave Oven")
                        .price(179.99)
                        .description("Microwave Oven with multiple cooking modes and defrost function.")
                        .quantity(BigInteger.valueOf(90))
                        .catalog(catalogs.get(4))
                        .material(materials.get(9))
                        .room(rooms.get(4))
                        .isActive(true)
                        .isFavorite(false)
                        .madeIn("France")
                        .descriptionEn("Microwave Oven with multiple cooking modes and defrost function.")
                        .descriptionUz("Ko'p pishirish rejimlari va defrost funktsiyasiga ega mikroto'lqinli pech.")
                        .descriptionRu("Микроволновая печь с несколькими режимами готовки и функцией разморозки.")
                        .descriptionCy("Oven Microdon gyda sawl modd coginio a swyddogaeth diffyrru.")
                        .nameEn("Microwave Oven")
                        .nameUz("Microwave Oven")
                        .nameRu("Микроволновая печь")
                        .nameCy("Oven Microdon")
                        .discounts(Arrays.asList(discounts.get(4)))
                        .colors(Arrays.asList(colors.get(9)))
                        .build()
                // Continue adding more products with all fields filled in
        );
    }

    private List<CallRequest> generateCallRequests(List<Product> products) {
        return Arrays.asList(
                CallRequest.builder().name("John Doe").phone("+123456789").product(products.get(0)).build(),
                CallRequest.builder().name("Jane Smith").phone("+987654321").product(products.get(1)).build(),
                CallRequest.builder().name("Robert Brown").phone("+1122334455").product(products.get(2)).build(),
                CallRequest.builder().name("Emily White").phone("+2233445566").product(products.get(3)).build(),
                CallRequest.builder().name("Michael Green").phone("+3344556677").product(products.get(4)).build(),
                CallRequest.builder().name("Alice Johnson").phone("+4455667788").product(products.get(5)).build(),
                CallRequest.builder().name("David Wilson").phone("+5566778899").product(products.get(6)).build(),
                CallRequest.builder().name("Sophia Davis").phone("+6677889900").product(products.get(7)).build(),
                CallRequest.builder().name("James Miller").phone("+7788990011").product(products.get(8)).build(),
                CallRequest.builder().name("Olivia Martinez").phone("+8899001122").product(products.get(9)).build()
        );
    }

    private List<User> generateUsers() {
        return Arrays.asList(
                User.builder().email("john.doe@example.com").dateJoined(ZonedDateTime.now()).password(passwordEncoder.encode("password123")).firstName("John").lastName("Doe").lastLogin(ZonedDateTime.now().minusDays(30)).phone("+1122334455").accountType(AccountType.phone).avatar("https://example.com/images/john_avatar.jpg").build(),
                User.builder().email("jane.smith@example.com").dateJoined(ZonedDateTime.now()).password(passwordEncoder.encode("password123")).firstName("Jane").lastName("Smith").lastLogin(ZonedDateTime.now().minusDays(30)).phone("+5566778899").accountType(AccountType.app).avatar("https://example.com/images/jane_avatar.jpg").build(),
                User.builder().email("robert.brown@example.com").dateJoined(ZonedDateTime.now()).password(passwordEncoder.encode("password123")).firstName("Robert").lastName("Brown").lastLogin(ZonedDateTime.now().minusDays(30)).phone("+6677889900").accountType(AccountType.google).avatar("https://example.com/images/robert_avatar.jpg").build(),
                User.builder().email("emily.white@example.com").dateJoined(ZonedDateTime.now()).password(passwordEncoder.encode("password123")).firstName("Emily").lastName("White").lastLogin(ZonedDateTime.now().minusDays(30)).phone("+7788990011").accountType(AccountType.phone).avatar("https://example.com/images/emily_avatar.jpg").build(),
                User.builder().email("michael.green@example.com").dateJoined(ZonedDateTime.now()).password(passwordEncoder.encode("password123")).firstName("Michael").lastName("Green").lastLogin(ZonedDateTime.now().minusDays(30)).phone("+8899001122").accountType(AccountType.app).avatar("https://example.com/images/michael_avatar.jpg").build()
        );
    }

    private static List<Card> generateCards(List<User> users) {
        return Arrays.asList(
                Card.builder().cvv("asd").expirationDate(new Date(12022024)).user(users.get(0)).cardholderName("Eshmat").cardNumber("AD1234").build(),
                Card.builder().cvv("dsa").expirationDate(new Date(12022024)).user(users.get(0)).cardholderName("Hikmat").cardNumber("AD1334").build(),
                Card.builder().cvv("df").expirationDate(new Date(12022024)).user(users.get(1)).cardholderName("Toshmat").cardNumber("AC1234").build(),
                Card.builder().cvv("zxv").expirationDate(new Date(12022024)).user(users.get(1)).cardholderName("Alice").cardNumber("AB6534").build(),
                Card.builder().cvv("bfs").expirationDate(new Date(12022024)).user(users.get(2)).cardholderName("Rob").cardNumber("AD1234").build(),
                Card.builder().cvv("wert").expirationDate(new Date(12022024)).user(users.get(2)).cardholderName("Ken").cardNumber("AB4334").build(),
                Card.builder().cvv("qwd").expirationDate(new Date(12022024)).user(users.get(3)).cardholderName("Kelly").cardNumber("AD1234").build(),
                Card.builder().cvv("lys").expirationDate(new Date(12022024)).user(users.get(3)).cardholderName("Tom").cardNumber("AA1234").build(),
                Card.builder().cvv("tyr").expirationDate(new Date(12022024)).user(users.get(4)).cardholderName("Bob").cardNumber("AA8934").build(),
                Card.builder().cvv("tyu").expirationDate(new Date(12022024)).user(users.get(4)).cardholderName("Rob").cardNumber("AD2334").build()
        );
    }

    private static List<Cart> generateCarts(List<User> users) {
        return Arrays.asList(
                Cart.builder().user(users.get(0)).isOrdered(false).build(),
                Cart.builder().user(users.get(1)).isOrdered(false).build(),
                Cart.builder().user(users.get(2)).isOrdered(false).build(),
                Cart.builder().user(users.get(3)).isOrdered(false).build(),
                Cart.builder().user(users.get(4)).isOrdered(false).build()
        );
    }

    private static List<Color> generateColors() {
        return Arrays.asList(
                Color.builder().name("Red").hexCode("#FF0000").build(),
                Color.builder().name("Green").hexCode("#00FF00").build(),
                Color.builder().name("Blue").hexCode("#0000FF").build(),
                Color.builder().name("Yellow").hexCode("#FFFF00").build(),
                Color.builder().name("Black").hexCode("#000000").build(),
                Color.builder().name("White").hexCode("#FFFFFF").build(),
                Color.builder().name("Purple").hexCode("#800080").build(),
                Color.builder().name("Orange").hexCode("#FFA500").build(),
                Color.builder().name("Pink").hexCode("#FFC0CB").build(),
                Color.builder().name("Gray").hexCode("#808080").build()
        );
    }

    private static List<Region> generateRegions() {
        return Arrays.asList(
                Region.builder().name("North").build(),
                Region.builder().name("South").build(),
                Region.builder().name("East").build(),
                Region.builder().name("West").build(),
                Region.builder().name("Central").build()
        );
    }

    private static List<District> generateDistricts(List<Region> regions) {
        return Arrays.asList(
                District.builder().name("Downtown").region(regions.get(0)).build(),
                District.builder().name("Uptown").region(regions.get(0)).build(),
                District.builder().name("Suburbia").region(regions.get(1)).build(),
                District.builder().name("Countryside").region(regions.get(1)).build(),
                District.builder().name("Seaside").region(regions.get(2)).build(),
                District.builder().name("Mountainview").region(regions.get(2)).build(),
                District.builder().name("Lakeside").region(regions.get(3)).build(),
                District.builder().name("Forest Glen").region(regions.get(3)).build(),
                District.builder().name("City Center").region(regions.get(4)).build(),
                District.builder().name("Historic District").region(regions.get(4)).build(),
                District.builder().name("Tech Park").region(regions.get(0)).build(),
                District.builder().name("Old Town").region(regions.get(1)).build(),
                District.builder().name("Greenfield").region(regions.get(2)).build(),
                District.builder().name("Riverdale").region(regions.get(3)).build(),
                District.builder().name("Harborview").region(regions.get(4)).build(),
                District.builder().name("West End").region(regions.get(0)).build(),
                District.builder().name("Northside").region(regions.get(1)).build(),
                District.builder().name("Eastside").region(regions.get(2)).build(),
                District.builder().name("Southside").region(regions.get(3)).build(),
                District.builder().name("Central Park").region(regions.get(4)).build(),
                District.builder().name("Southgate").region(regions.get(0)).build(),
                District.builder().name("Northgate").region(regions.get(1)).build(),
                District.builder().name("Eastgate").region(regions.get(2)).build(),
                District.builder().name("Westgate").region(regions.get(3)).build(),
                District.builder().name("Centralgate").region(regions.get(4)).build(),
                District.builder().name("Oceanview").region(regions.get(4)).build(),
                District.builder().name("Springfield Heights").region(regions.get(0)).build(),
                District.builder().name("Greenwood").region(regions.get(1)).build(),
                District.builder().name("Blue Ridge").region(regions.get(2)).build(),
                District.builder().name("Pleasantville").region(regions.get(3)).build(),
                District.builder().name("Sunnydale").region(regions.get(4)).build()
        );
    }

    public List<Order> generateOrders() {
        Random random = new Random();
        List<User> users = userRepository.findAll();


        return Arrays.stream(new int[20])
                .mapToObj(i -> Order.builder()
                        .status(statuses.get(random.nextInt(statuses.size())))
                        .user((User) userRepository.findByEmail("farangizxon2004@gmail.com"))
                        .createdAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                        .updatedAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Delivery> generateDeliveries(List<Order> orders) {
        Random random = new Random();

        return orders.stream()
                .map(order -> Delivery.builder()
                        .name("Recipient " + random.nextInt(100))
                        .phone("+1" + random.nextInt(1000000000))
                        .address("Address " + random.nextInt(100))
                        .entrance("Entrance " + random.nextInt(5))
                        .floor("Floor " + random.nextInt(10))
                        .apartment("Apartment " + random.nextInt(50))
                        .deliveryType(deliveryTypes.get(random.nextInt(deliveryTypes.size())))
                        .deliveryDate(new Date(System.currentTimeMillis() + random.nextInt(30) * 24 * 60 * 60 * 1000L)) // Random future date
                        .isDelivered(random.nextBoolean())
                        .order(order)
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Shop> generateShops() {
        Random random = new Random();

        return Arrays.stream(new int[10])
                .mapToObj(i -> Shop.builder()
                        .name("Shop " + (i + 1))
                        .logo("https://example.com/images/shop_logo_" + (i + 1) + ".jpg")
                        .banner("https://example.com/images/shop_banner_" + (i + 1) + ".jpg")
                        .phone("+1" + random.nextInt(1000000000))
                        .email("shop" + (i + 1) + "@example.com")
                        .description("Description for Shop " + (i + 1))
                        .instagram("https://instagram.com/shop" + (i + 1))
                        .telegram("https://t.me/shop" + (i + 1))
                        .facebook("https://facebook.com/shop" + (i + 1))
                        .owner((User) userRepository.findByEmail("farangizxon2004@gmail.com"))
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DeliveryPoint> generateDeliveryPoints(List<Shop> shops) {
        Random random = new Random();

        return shops.stream()
                .map(shop -> DeliveryPoint.builder()
                        .name("Delivery Point " + (shops.indexOf(shop) + 1))
                        .address("Address " + (shops.indexOf(shop) + 1))
                        .city("City " + (shops.indexOf(shop) + 1))
                        .state("State " + (shops.indexOf(shop) + 1))
                        .country("Country " + (shops.indexOf(shop) + 1))
                        .postalCode("PostalCode" + (shops.indexOf(shop) + 1))
                        .lon(random.nextDouble() * 180 - 90) // Random longitude
                        .lat(random.nextDouble() * 360 - 180) // Random latitude
                        .shop(shop)
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Discount> generateDiscounts() {
        Random random = new Random();

        return Arrays.stream(new int[20])
                .mapToObj(i -> Discount.builder()
                        .code("DISCOUNT" + (i + 1))
                        .discountType(discountTypes.get(random.nextInt(discountTypes.size())))
                        .amount(random.nextDouble() * 100) // Random amount between 0 and 100
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Faq> generateFaqs() {
        return Arrays.asList(
                Faq.builder()
                        .title("What is our return policy?")
                        .titleUz("Bizning qaytish siyosatimiz nima?")
                        .titleOz("Bizning qaytish siyosatimiz nima?")
                        .titleRu("Какова наша политика возврата?")
                        .titleEn("What is our return policy?")
                        .description("Our return policy allows returns within 30 days of purchase.")
                        .descriptionUz("Bizning qaytish siyosatimiz xarid qilingan kundan 30 kun ichida qaytarishga imkon beradi.")
                        .descriptionOz("Bizning qaytish siyosatimiz xarid qilingan kundan 30 kun ichida qaytarishga imkon beradi.")
                        .descriptionRu("Наша политика возврата позволяет возврат в течение 30 дней после покупки.")
                        .descriptionEn("Our return policy allows returns within 30 days of purchase.")
                        .build(),
                Faq.builder()
                        .title("How can I track my order?")
                        .titleUz("Buyurtmamni qanday kuzatishim mumkin?")
                        .titleOz("Buyurtmamni qanday kuzatishim mumkin?")
                        .titleRu("Как я могу отслеживать мой заказ?")
                        .titleEn("How can I track my order?")
                        .description("You can track your order using the tracking number provided in your order confirmation email.")
                        .descriptionUz("Buyurtmangizni tasdiqlash elektron pochtasida berilgan kuzatish raqami yordamida kuzatishingiz mumkin.")
                        .descriptionOz("Buyurtmangizni tasdiqlash elektron pochtasida berilgan kuzatish raqami yordamida kuzatishingiz mumkin.")
                        .descriptionRu("Вы можете отслеживать свой заказ, используя номер для отслеживания, указанный в электронном письме с подтверждением заказа.")
                        .descriptionEn("You can track your order using the tracking number provided in your order confirmation email.")
                        .build(),
                Faq.builder()
                        .title("What payment methods do you accept?")
                        .titleUz("Qanday to'lov usullarini qabul qilasiz?")
                        .titleOz("Qanday to'lov usullarini qabul qilasiz?")
                        .titleRu("Какие способы оплаты вы принимаете?")
                        .titleEn("What payment methods do you accept?")
                        .description("We accept various payment methods including credit cards, debit cards, and PayPal.")
                        .descriptionUz("Biz kredit kartalari, debet kartalari va PayPal kabi turli to'lov usullarini qabul qilamiz.")
                        .descriptionOz("Biz kredit kartalari, debet kartalari va PayPal kabi turli to'lov usullarini qabul qilamiz.")
                        .descriptionRu("Мы принимаем различные способы оплаты, включая кредитные карты, дебетовые карты и PayPal.")
                        .descriptionEn("We accept various payment methods including credit cards, debit cards, and PayPal.")
                        .build(),
                Faq.builder()
                        .title("Can I change or cancel my order?")
                        .titleUz("Buyurtmamni o'zgartirish yoki bekor qilish mumkinmi?")
                        .titleOz("Buyurtmamni o'zgartirish yoki bekor qilish mumkinmi?")
                        .titleRu("Могу ли я изменить или отменить мой заказ?")
                        .titleEn("Can I change or cancel my order?")
                        .description("You can change or cancel your order within 24 hours of placing it. Please contact our customer service team.")
                        .descriptionUz("Siz buyurtmangizni berilganidan 24 soat ichida o'zgartirishingiz yoki bekor qilishingiz mumkin. Iltimos, mijozlarga xizmat ko'rsatish jamoamiz bilan bog'laning.")
                        .descriptionOz("Siz buyurtmangizni berilganidan 24 soat ichida o'zgartirishingiz yoki bekor qilishingiz mumkin. Iltimos, mijozlarga xizmat ko'rsatish jamoamiz bilan bog'laning.")
                        .descriptionRu("Вы можете изменить или отменить свой заказ в течение 24 часов с момента его размещения. Пожалуйста, свяжитесь с нашей службой поддержки.")
                        .descriptionEn("You can change or cancel your order within 24 hours of placing it. Please contact our customer service team.")
                        .build(),
                Faq.builder()
                        .title("Do you offer international shipping?")
                        .titleUz("Xalqaro yetkazib berishni taklif qilasizmi?")
                        .titleOz("Xalqaro yetkazib berishni taklif qilasizmi?")
                        .titleRu("Предлагаете ли вы международную доставку?")
                        .titleEn("Do you offer international shipping?")
                        .description("Yes, we offer international shipping to most countries. Shipping fees and delivery times vary.")
                        .descriptionUz("Ha, biz aksariyat mamlakatlarga xalqaro yetkazib berishni taklif qilamiz. Yetkazib berish haqidagi to'lovlar va vaqtlar farq qiladi.")
                        .descriptionOz("Ha, biz aksariyat mamlakatlarga xalqaro yetkazib berishni taklif qilamiz. Yetkazib berish haqidagi to'lovlar va vaqtlar farq qiladi.")
                        .descriptionRu("Да, мы предлагаем международную доставку в большинство стран. Стоимость доставки и время доставки могут различаться.")
                        .descriptionEn("Yes, we offer international shipping to most countries. Shipping fees and delivery times vary.")
                        .build(),
                Faq.builder()
                        .title("How can I contact customer support?")
                        .titleUz("Mijozlarga xizmat ko'rsatish bilan qanday bog'lanishim mumkin?")
                        .titleOz("Mijozlarga xizmat ko'rsatish bilan qanday bog'lanishim mumkin?")
                        .titleRu("Как я могу связаться со службой поддержки?")
                        .titleEn("How can I contact customer support?")
                        .description("You can contact customer support via email at support@example.com or by phone at +1234567890.")
                        .descriptionUz("Siz mijozlarga xizmat ko'rsatish bilan support@example.com elektron pochtasi orqali yoki +1234567890 telefon raqami orqali bog'lanishingiz mumkin.")
                        .descriptionOz("Siz mijozlarga xizmat ko'rsatish bilan support@example.com elektron pochtasi orqali yoki +1234567890 telefon raqami orqali bog'lanishingiz mumkin.")
                        .descriptionRu("Вы можете связаться со службой поддержки по электронной почте support@example.com или по телефону +1234567890.")
                        .descriptionEn("You can contact customer support via email at support@example.com or by phone at +1234567890.")
                        .build(),
                Faq.builder()
                        .title("What is your warranty policy?")
                        .titleUz("Sizning kafolat siyosatingiz qanday?")
                        .titleOz("Sizning kafolat siyosatingiz qanday?")
                        .titleRu("Какова ваша гарантийная политика?")
                        .titleEn("What is your warranty policy?")
                        .description("We offer a one-year warranty on all our products. For more details, please refer to our warranty policy page.")
                        .descriptionUz("Biz barcha mahsulotlarimizga bir yillik kafolat taklif qilamiz. Batafsil ma'lumot uchun kafolat siyosati sahifasiga murojaat qiling.")
                        .descriptionOz("Biz barcha mahsulotlarimizga bir yillik kafolat taklif qilamiz. Batafsil ma'lumot uchun kafolat siyosati sahifasiga murojaat qiling.")
                        .descriptionRu("Мы предоставляем однолетнюю гарантию на все наши продукты. Для получения дополнительной информации, пожалуйста, обратитесь к нашей странице гарантийной политики.")
                        .descriptionEn("We offer a one-year warranty on all our products. For more details, please refer to our warranty policy page.")
                        .build(),
                Faq.builder()
                        .title("Can I use multiple discount codes?")
                        .titleUz("Bir nechta chegirma kodlarini ishlata olamanmi?")
                        .titleOz("Bir nechta chegirma kodlarini ishlata olamanmi?")
                        .titleRu("Могу ли я использовать несколько кодов скидок?")
                        .titleEn("Can I use multiple discount codes?")
                        .description("Only one discount code can be used per order. Please choose the one that offers the best value.")
                        .descriptionUz("Bir buyurtma uchun faqat bitta chegirma kodi ishlatilishi mumkin. Eng yaxshi qiymatni taklif qiladigan kodni tanlang.")
                        .descriptionOz("Bir buyurtma uchun faqat bitta chegirma kodi ishlatilishi mumkin. Eng yaxshi qiymatni taklif qiladigan kodni tanlang.")
                        .descriptionRu("Можно использовать только один код скидки на заказ. Пожалуйста, выберите тот, который предлагает наилучшую ценность.")
                        .descriptionEn("Only one discount code can be used per order. Please choose the one that offers the best value.")
                        .build(),
                Faq.builder()
                        .title("Do you offer gift cards?")
                        .titleUz("Siz sovg'a kartalari taklif qilasizmi?")
                        .titleOz("Siz sovg'a kartalari taklif qilasizmi?")
                        .titleRu("Предлагаете ли вы подарочные карты?")
                        .titleEn("Do you offer gift cards?")
                        .description("Yes, we offer gift cards in various denominations. They can be purchased through our website.")
                        .descriptionUz("Ha, biz turli miqdordagi sovg'a kartalarini taklif qilamiz. Ularni veb-saytimiz orqali sotib olish mumkin.")
                        .descriptionOz("Ha, biz turli miqdordagi sovg'a kartalarini taklif qilamiz. Ularni veb-saytimiz orqali sotib olish mumkin.")
                        .descriptionRu("Да, мы предлагаем подарочные карты разных номиналов. Их можно приобрести через наш веб-сайт.")
                        .descriptionEn("Yes, we offer gift cards in various denominations. They can be purchased through our website.")
                        .build(),
                Faq.builder()
                        .title("What is your privacy policy?")
                        .titleUz("Sizning maxfiylik siyosatingiz qanday?")
                        .titleOz("Sizning maxfiylik siyosatingiz qanday?")
                        .titleRu("Какова ваша политика конфиденциальности?")
                        .titleEn("What is your privacy policy?")
                        .description("Our privacy policy details how we collect, use, and protect your personal information. Please review it on our website.")
                        .descriptionUz("Bizning maxfiylik siyosatimiz sizning shaxsiy ma'lumotlaringizni qanday to'plash, ishlatish va himoya qilishimizni batafsil bayon etadi. Uni veb-saytimizda ko'rib chiqing.")
                        .descriptionOz("Bizning maxfiylik siyosatimiz sizning shaxsiy ma'lumotlaringizni qanday to'plash, ishlatish va himoya qilishimizni batafsil bayon etadi. Uni veb-saytimizda ko'rib chiqing.")
                        .descriptionRu("Наша политика конфиденциальности описывает, как мы собираем, используем и защищаем вашу личную информацию. Пожалуйста, ознакомьтесь с ней на нашем веб-сайте.")
                        .descriptionEn("Our privacy policy details how we collect, use, and protect your personal information. Please review it on our website.")
                        .build(),
                Faq.builder()
                        .title("How do I update my account information?")
                        .titleUz("Hisob ma'lumotlarimni qanday yangilayman?")
                        .titleOz("Hisob ma'lumotlarimni qanday yangilayman?")
                        .titleRu("Как я могу обновить информацию о своей учетной записи?")
                        .titleEn("How do I update my account information?")
                        .description("You can update your account information by logging into your account and navigating to the 'Account Settings' section.")
                        .descriptionUz("Hisob ma'lumotlaringizni yangilash uchun hisobingizga kirib, 'Hisob sozlamalari' bo'limiga o'ting.")
                        .descriptionOz("Hisob ma'lumotlaringizni yangilash uchun hisobingizga kirib, 'Hisob sozlamalari' bo'limiga o'ting.")
                        .descriptionRu("Вы можете обновить информацию о своей учетной записи, войдя в свою учетную запись и перейдя в раздел 'Настройки учетной записи'.")
                        .descriptionEn("You can update your account information by logging into your account and navigating to the 'Account Settings' section.")
                        .build(),
                Faq.builder()
                        .title("Can I subscribe to your newsletter?")
                        .titleUz("Sizning yangiliklaringizga obuna bo'lishim mumkinmi?")
                        .titleOz("Sizning yangiliklaringizga obuna bo'lishim mumkinmi?")
                        .titleRu("Могу ли я подписаться на вашу рассылку?")
                        .titleEn("Can I subscribe to your newsletter?")
                        .description("Yes, you can subscribe to our newsletter by entering your email address in the subscription box on our website.")
                        .descriptionUz("Ha, siz veb-saytimizdagi obuna qutisiga elektron pochtangizni kiritib, yangiliklarimizga obuna bo'lishingiz mumkin.")
                        .descriptionOz("Ha, siz veb-saytimizdagi obuna qutisiga elektron pochtangizni kiritib, yangiliklarimizga obuna bo'lishingiz mumkin.")
                        .descriptionRu("Да, вы можете подписаться на нашу рассылку, введя свой адрес электронной почты в поле подписки на нашем веб-сайте.")
                        .descriptionEn("Yes, you can subscribe to our newsletter by entering your email address in the subscription box on our website.")
                        .build(),
                Faq.builder()
                        .title("What should I do if I receive a damaged product?")
                        .titleUz("Agar men zarar ko'rgan mahsulotni olsam, nima qilishim kerak?")
                        .titleOz("Agar men zarar ko'rgan mahsulotni olsam, nima qilishim kerak?")
                        .titleRu("Что делать, если я получил поврежденный товар?")
                        .titleEn("What should I do if I receive a damaged product?")
                        .description("Please contact our customer support immediately if you receive a damaged product. We will assist you with a replacement or refund.")
                        .descriptionUz("Agar zarar ko'rgan mahsulotni olsangiz, iltimos, darhol mijozlarga xizmat ko'rsatishimiz bilan bog'laning. Biz sizga almashtirish yoki qaytarish bilan yordam beramiz.")
                        .descriptionOz("Agar zarar ko'rgan mahsulotni olsangiz, iltimos, darhol mijozlarga xizmat ko'rsatishimiz bilan bog'laning. Biz sizga almashtirish yoki qaytarish bilan yordam beramiz.")
                        .descriptionRu("Пожалуйста, свяжитесь с нашей службой поддержки, если вы получили поврежденный товар. Мы поможем вам с заменой или возвратом.")
                        .descriptionEn("Please contact our customer support immediately if you receive a damaged product. We will assist you with a replacement or refund.")
                        .build(),
                Faq.builder()
                        .title("Do you offer a loyalty program?")
                        .titleUz("Siz mukofot dasturini taklif qilasizmi?")
                        .titleOz("Siz mukofot dasturini taklif qilasizmi?")
                        .titleRu("Предлагаете ли вы программу лояльности?")
                        .titleEn("Do you offer a loyalty program?")
                        .description("Yes, we have a loyalty program that rewards you with points for every purchase. Points can be redeemed for discounts.")
                        .descriptionUz("Ha, biz har bir xarid uchun sizga ballar beradigan mukofot dasturimiz mavjud. Ballar chegirmalar uchun ishlatilishi mumkin.")
                        .descriptionOz("Ha, biz har bir xarid uchun sizga ballar beradigan mukofot dasturimiz mavjud. Ballar chegirmalar uchun ishlatilishi mumkin.")
                        .descriptionRu("Да, у нас есть программа лояльности, которая вознаграждает вас баллами за каждую покупку. Баллы можно обменивать на скидки.")
                        .descriptionEn("Yes, we have a loyalty program that rewards you with points for every purchase. Points can be redeemed for discounts.")
                        .build()
        );
    }

    public List<Material> generateMaterials() {
            return Arrays.asList(
                    Material.builder()
                            .name("Cotton")
                            .nameRu("Хлопок")
                            .nameEn("Cotton")
                            .nameUz("Paxta")
                            .nameCy("Cotwm")
                            .build(),
                    Material.builder()
                            .name("Leather")
                            .nameRu("Кожа")
                            .nameEn("Leather")
                            .nameUz("Teri")
                            .nameCy("Lledr")
                            .build(),
                    Material.builder()
                            .name("Wool")
                            .nameRu("Шерсть")
                            .nameEn("Wool")
                            .nameUz("Jun")
                            .nameCy("Wool")
                            .build(),
                    Material.builder()
                            .name("Silk")
                            .nameRu("Шелк")
                            .nameEn("Silk")
                            .nameUz("Makkajo")
                            .nameCy("Silg")
                            .build(),
                    Material.builder()
                            .name("Polyester")
                            .nameRu("Полиэстер")
                            .nameEn("Polyester")
                            .nameUz("Poliester")
                            .nameCy("Polyester")
                            .build(),
                    Material.builder()
                            .name("Nylon")
                            .nameRu("Нейлон")
                            .nameEn("Nylon")
                            .nameUz("Naylon")
                            .nameCy("Nylon")
                            .build(),
                    Material.builder()
                            .name("Linen")
                            .nameRu("Лён")
                            .nameEn("Linen")
                            .nameUz("Juta")
                            .nameCy("Linen")
                            .build(),
                    Material.builder()
                            .name("Velvet")
                            .nameRu("Велюр")
                            .nameEn("Velvet")
                            .nameUz("Velvet")
                            .nameCy("Velvet")
                            .build(),
                    Material.builder()
                            .name("Rayon")
                            .nameRu("Район")
                            .nameEn("Rayon")
                            .nameUz("Rayon")
                            .nameCy("Rayon")
                            .build(),
                    Material.builder()
                            .name("Acrylic")
                            .nameRu("Акрил")
                            .nameEn("Acrylic")
                            .nameUz("Akril")
                            .nameCy("Acrylic")
                            .build(),
                    Material.builder()
                            .name("Chiffon")
                            .nameRu("Шифон")
                            .nameEn("Chiffon")
                            .nameUz("Shifon")
                            .nameCy("Chiffon")
                            .build(),
                    Material.builder()
                            .name("Spandex")
                            .nameRu("Спандекс")
                            .nameEn("Spandex")
                            .nameUz("Spandeks")
                            .nameCy("Spandex")
                            .build(),
                    Material.builder()
                            .name("Fleece")
                            .nameRu("Флис")
                            .nameEn("Fleece")
                            .nameUz("Flis")
                            .nameCy("Fleece")
                            .build(),
                    Material.builder()
                            .name("Satin")
                            .nameRu("Сатин")
                            .nameEn("Satin")
                            .nameUz("Satin")
                            .nameCy("Satin")
                            .build(),
                    Material.builder()
                            .name("Canvas")
                            .nameRu("Канва")
                            .nameEn("Canvas")
                            .nameUz("Kanvas")
                            .nameCy("Canvas")
                            .build()
            );
        }

    public List<SearchHistory> generateSearchHistories() {
        List<User> users = userRepository.findAll();
        User specificUser = (User) userRepository.findByEmail("farangizxon2004@gmail.com");
        List<SearchHistory> searchHistories = users.stream()
                .flatMap(user -> IntStream.range(0, 2)  // 2 search histories per user
                        .mapToObj(i -> SearchHistory.builder()
                                .text("Search history entry " + i + " for user " + user.getId())
                                .user(user)
                                .build())
                )
                .collect(Collectors.toList());

        // 5 search histories for user with id 0
        List<SearchHistory> specificUserSearchHistories = IntStream.range(0, 5)
                .mapToObj(i -> SearchHistory.builder()
                        .text("Specific user search history entry " + i)
                        .user(specificUser)
                        .build())
                .toList();

        searchHistories.addAll(specificUserSearchHistories);
        return searchHistories;
    }

    public List<Room> generateRooms() {
        return Arrays.asList(
                Room.builder()
                        .name("Living Room")
                        .nameEn("Living Room")
                        .nameUz("Oshxona")
                        .nameRu("Гостиная")
                        .nameCy("Ystafell fyw")
                        .banner("https://example.com/images/living_room_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Bedroom")
                        .nameEn("Bedroom")
                        .nameUz("Yotoqxona")
                        .nameRu("Спальня")
                        .nameCy("Ystafell wely")
                        .banner("https://example.com/images/bedroom_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Kitchen")
                        .nameEn("Kitchen")
                        .nameUz("Oshxona")
                        .nameRu("Кухня")
                        .nameCy("Cegin")
                        .banner("https://example.com/images/kitchen_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Bathroom")
                        .nameEn("Bathroom")
                        .nameUz("Hojatxona")
                        .nameRu("Ванная")
                        .nameCy("Ystafell ymolchiant")
                        .banner("https://example.com/images/bathroom_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Office")
                        .nameEn("Office")
                        .nameUz("Ofis")
                        .nameRu("Офис")
                        .nameCy("Gweithdy")
                        .banner("https://example.com/images/office_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Dining Room")
                        .nameEn("Dining Room")
                        .nameUz("Ovqatlanish xonasi")
                        .nameRu("Столовая")
                        .nameCy("Ystafell fwyta")
                        .banner("https://example.com/images/dining_room_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Library")
                        .nameEn("Library")
                        .nameUz("Kutubxona")
                        .nameRu("Библиотека")
                        .nameCy("Llyfrgell")
                        .banner("https://example.com/images/library_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Garage")
                        .nameEn("Garage")
                        .nameUz("Garaž")
                        .nameRu("Гараж")
                        .nameCy("Garc")
                        .banner("https://example.com/images/garage_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Gym")
                        .nameEn("Gym")
                        .nameUz("Sport zal")
                        .nameRu("Спортзал")
                        .nameCy("Gim")
                        .banner("https://example.com/images/gym_banner.jpg")
                        .build(),
                Room.builder()
                        .name("Balcony")
                        .nameEn("Balcony")
                        .nameUz("Balkon")
                        .nameRu("Балкон")
                        .nameCy("Balcôni")
                        .banner("https://example.com/images/balcony_banner.jpg")
                        .build()
        );
    }

    public List<SiteReview> generateSiteReviews() {
        return Arrays.asList(
                SiteReview.builder()
                        .name("John Doe")
                        .text("Great service! Highly recommend.")
                        .build(),
                SiteReview.builder()
                        .name("Jane Smith")
                        .text("The product quality is excellent.")
                        .build(),
                SiteReview.builder()
                        .name("Robert Brown")
                        .text("Very satisfied with the customer support.")
                        .build(),
                SiteReview.builder()
                        .name("Emily White")
                        .text("Fast shipping and great packaging.")
                        .build(),
                SiteReview.builder()
                        .name("Michael Green")
                        .text("The website is very user-friendly.")
                        .build(),
                SiteReview.builder()
                        .name("Anna Black")
                        .text("Good prices and reliable service.")
                        .build(),
                SiteReview.builder()
                        .name("David Wilson")
                        .text("Had a minor issue, but it was resolved quickly.")
                        .build(),
                SiteReview.builder()
                        .name("Sophia Lee")
                        .text("Will definitely shop here again.")
                        .build(),
                SiteReview.builder()
                        .name("James Anderson")
                        .text("The quality of the items is as described.")
                        .build(),
                SiteReview.builder()
                        .name("Olivia Martinez")
                        .text("Great experience, but delivery took a bit longer.")
                        .build(),
                SiteReview.builder()
                        .name("William Thomas")
                        .text("Very professional and easy to communicate with.")
                        .build(),
                SiteReview.builder()
                        .name("Ava Harris")
                        .text("The selection of products is fantastic.")
                        .build(),
                SiteReview.builder()
                        .name("Liam Clark")
                        .text("I love the customer service here.")
                        .build(),
                SiteReview.builder()
                        .name("Isabella Lewis")
                        .text("The website is a bit slow, but the service is great.")
                        .build(),
                SiteReview.builder()
                        .name("Ethan Walker")
                        .text("Highly recommend this store to everyone.")
                        .build(),
                SiteReview.builder()
                        .name("Mia Scott")
                        .text("The staff is friendly and helpful.")
                        .build(),
                SiteReview.builder()
                        .name("Alexander Young")
                        .text("Had a pleasant shopping experience.")
                        .build(),
                SiteReview.builder()
                        .name("Charlotte Allen")
                        .text("Products are just as described.")
                        .build(),
                SiteReview.builder()
                        .name("Benjamin King")
                        .text("Excellent service and fast delivery.")
                        .build(),
                SiteReview.builder()
                        .name("Amelia Wright")
                        .text("Everything was perfect, thanks!")
                        .build()
        );
    }

    public List<OrderItem> generateOrderItems(List<Order> orders) {
        Random random = new Random();
        List<Product> products = productRepository.findAll();

        List<OrderItem> orderItems = new ArrayList<>();

        for (Order order : orders) {
            int numberOfItems = random.nextInt(3) + 2;
            for (int i = 0; i < numberOfItems; i++) {
                OrderItem orderItem = OrderItem.builder()
                        .quantity(random.nextInt(5) + 1)
                        .order(order)
                        .product(products.get(random.nextInt(10)))
                        .createdAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                        .updatedAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                        .build();

                orderItems.add(orderItem);
            }
        }

        return orderItems;
    }

    public List<Payment> generatePayments() {
        Random random = new Random();
        List<User> users = userRepository.findAll();

        return users.stream()
                .flatMap(user -> Stream.generate(() -> Payment.builder()
                                .amount(BigDecimal.valueOf(random.nextDouble() * 1000).setScale(2, RoundingMode.HALF_UP))
                                .timestamp(ZonedDateTime.now().minusDays(random.nextInt(30)))
                                .success(random.nextBoolean())
                                .user(user)
                                .createdAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                                .updatedAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                                .build())
                        .limit(3))
                .collect(Collectors.toList());
    }

    public List<Review> generateReviews() {
        Random random = new Random();
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Rate> rates = Arrays.asList(Rate.ONE, Rate.TWO, Rate.THREE, Rate.FOUR, Rate.FIVE, Rate.ONE_POINT_FIVE, Rate.TWO_POINT_FIVE, Rate.THREE_POINT_FIVE, Rate.FOUR_POINT_FIVE);

        return products.stream()
                .flatMap(product -> Stream.generate(() -> Review.builder()
                                .rate(rates.get(random.nextInt(rates.size())))
                                .text("This is a review for product " + product.getName() + ". The product is " + randomReviewText())
                                .product(product)
                                .user(users.get(random.nextInt(users.size())))
                                .createdAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                                .updatedAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                                .build())
                        .limit(2))
                .collect(Collectors.toList());
    }

    private String randomReviewText() {
        List<String> reviews = Arrays.asList(
                "excellent", "average", "good but could be better", "not what I expected", "fantastic value for money"
        );
        return reviews.get(new Random().nextInt(reviews.size()));
    }

    public List<ProductImage> generateProductImages() {
        List<Product> products = productRepository.findAll();
        List<String> imageUrls = Arrays.asList(
                "https://example.com/images/product1.jpg",
                "https://example.com/images/product2.jpg",
                "https://example.com/images/product3.jpg",
                "https://example.com/images/product4.jpg",
                "https://example.com/images/product5.jpg",
                "https://example.com/images/product6.jpg",
                "https://example.com/images/product7.jpg",
                "https://example.com/images/product8.jpg",
                "https://example.com/images/product9.jpg",
                "https://example.com/images/product10.jpg",
                "https://example.com/images/product11.jpg",
                "https://example.com/images/product12.jpg",
                "https://example.com/images/product13.jpg",
                "https://example.com/images/product14.jpg",
                "https://example.com/images/product15.jpg",
                "https://example.com/images/product16.jpg",
                "https://example.com/images/product17.jpg",
                "https://example.com/images/product18.jpg",
                "https://example.com/images/product19.jpg",
                "https://example.com/images/product20.jpg",
                "https://example.com/images/product21.jpg",
                "https://example.com/images/product22.jpg",
                "https://example.com/images/product23.jpg",
                "https://example.com/images/product24.jpg",
                "https://example.com/images/product25.jpg",
                "https://example.com/images/product26.jpg",
                "https://example.com/images/product27.jpg",
                "https://example.com/images/product28.jpg",
                "https://example.com/images/product29.jpg",
                "https://example.com/images/product30.jpg"
        );

        return IntStream.range(0, products.size())
                .mapToObj(i -> ProductImage.builder()
                        .image(imageUrls.get(i))
                        .product(products.get(i))
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Showroom> generateShowrooms() {
        Random random = new Random();
        List<Shop> shops = shopRepository.findAll();
        List<Product> products = productRepository.findAll();

        List<String> positions = Arrays.asList("Front Entrance", "Back Corner", "Main Hall", "Second Floor", "Near Exit");
        List<String> banners = Arrays.asList(
                "https://example.com/banners/showroom1.jpg",
                "https://example.com/banners/showroom2.jpg",
                "https://example.com/banners/showroom3.jpg",
                "https://example.com/banners/showroom4.jpg",
                "https://example.com/banners/showroom5.jpg",
                "https://example.com/banners/showroom6.jpg",
                "https://example.com/banners/showroom7.jpg",
                "https://example.com/banners/showroom8.jpg",
                "https://example.com/banners/showroom9.jpg",
                "https://example.com/banners/showroom10.jpg"
        );

        return IntStream.range(0, 10)
                .mapToObj(i -> Showroom.builder()
                        .banner(banners.get(i))
                        .shop(shops.get(random.nextInt(shops.size())))
                        .position(positions.get(random.nextInt(positions.size())))
                        .products(products.subList(0, random.nextInt(products.size())))
                        .build())
                .collect(Collectors.toList());
    }

    public List<WishList> generateWishLists() {
        Random random = new Random();
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        return users.stream()
                .map(user -> WishList.builder()
                        .user(user)
                        .product(products.get(random.nextInt(products.size())))
                        .createdAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                        .updatedAt(ZonedDateTime.now().minusDays(random.nextInt(30)))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Upholster> generateUpholsters() {
        return Arrays.asList(
                Upholster.builder()
                        .name("Leather")
                        .nameRu("Кожа")
                        .nameEn("Leather")
                        .nameUz("Terida")
                        .nameCy("Lledr")
                        .build(),

                Upholster.builder()
                        .name("Fabric")
                        .nameRu("Ткань")
                        .nameEn("Fabric")
                        .nameUz("Matolar")
                        .nameCy("Ffabrig")
                        .build(),

                Upholster.builder()
                        .name("Velvet")
                        .nameRu("Вельвет")
                        .nameEn("Velvet")
                        .nameUz("Mato")
                        .nameCy("Ffloc")
                        .build(),

                Upholster.builder()
                        .name("Microfiber")
                        .nameRu("Микрофибра")
                        .nameEn("Microfiber")
                        .nameUz("Mikrofiber")
                        .nameCy("Microffibra")
                        .build(),

                Upholster.builder()
                        .name("Suede")
                        .nameRu("Замша")
                        .nameEn("Suede")
                        .nameUz("Zamsha")
                        .nameCy("Suede")
                        .build()
        );
    }

    public List<ShowroomProduct> generateShowroomProducts() {
        List<Product> products = productRepository.findAll();
        List<Showroom> showrooms = showroomRepository.findAll();

        return products.stream()
                .flatMap(product -> showrooms.stream()
                        .map(showroom -> ShowroomProduct.builder()
                                .product(product)
                                .showroom(showroom)
                                .build()))
                .collect(Collectors.toList());
    }

    public List<ShopShowroomproduct> generateShopShowroomProducts() {
        List<ShowroomProduct> showroomProducts = showroomProductRepository.findAll();
        List<Shop> shops = shopRepository.findAll();

        Random random = new Random();

        return showroomProducts.stream()
                .flatMap(showroomProduct -> shops.stream()
                        .map(shop -> ShopShowroomproduct.builder()
                                .x(random.nextDouble() * 100)
                                .y(random.nextDouble() * 100)
                                .showroomProduct(showroomProduct)
                                .build()))
                .collect(Collectors.toList());
    }

}
