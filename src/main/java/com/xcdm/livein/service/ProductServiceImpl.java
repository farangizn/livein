package com.xcdm.livein.service;

import com.xcdm.livein.entity.Product;
import com.xcdm.livein.interfaces.ProductService;
import com.xcdm.livein.repo.ProductRepository;
import com.xcdm.livein.repo.ProductRepositorySpec;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepositorySpec productRepositorySpec;

    @Override
    public List<Product> findProducts(String name, String price, String minPrice, String maxPrice, String catalog, String room, String colorsHexCode, String material, String shop, String random, String orderBy, int limit, int offset) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (price != null) {
                try {
                    Double priceValue = Double.valueOf(price);
                    predicates.add(criteriaBuilder.equal(root.get("price"), priceValue));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid price value");
                }
            }

            if (minPrice != null) {
                try {
                    Double minPriceValue = Double.valueOf(minPrice);
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPriceValue));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid minPrice");
                }
            }

            if (maxPrice != null) {
                try {
                    Double maxPriceValue = Double.valueOf(maxPrice);
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPriceValue));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid maxPrice");
                }
            }

            if (catalog != null) {
                try {
                    Long catalogId = Long.valueOf(catalog);
                    predicates.add(criteriaBuilder.equal(root.get("catalog").get("id"), catalogId));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid catalog id");
                }
            }

            if (room != null) {
                try {
                    Long roomId = Long.valueOf(room);
                    predicates.add(criteriaBuilder.equal(root.get("room").get("id"), roomId));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid room id");
                }
            }

            if (colorsHexCode != null) {
                predicates.add(root.join("colors").get("hexCode").in(colorsHexCode));
            }

            if (material != null) {
                try {
                    Long materialId = Long.valueOf(material);  // Changed to Long if IDs are Long
                    predicates.add(criteriaBuilder.equal(root.get("material").get("id"), materialId));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid id");
                }
            }

            if (shop != null) {
                try {
                    Long shopId = Long.valueOf(shop);  // Changed to Long if IDs are Long
                    predicates.add(criteriaBuilder.equal(root.get("shop").get("id"), shopId));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("invalid id");
                }
            }

            if (random != null && random.equalsIgnoreCase("true")) {
                query.orderBy(criteriaBuilder.asc(criteriaBuilder.function("RANDOM", Double.class)));
            }

            if (orderBy != null) {
                if (orderBy.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get("name")));
                } else if (orderBy.equalsIgnoreCase("desc")) {
                    query.orderBy(criteriaBuilder.desc(root.get("name")));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return productRepositorySpec.findAll(spec);
    }
}
