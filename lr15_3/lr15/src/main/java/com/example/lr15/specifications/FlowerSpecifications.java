package com.example.lr15.specifications;

import com.example.lr15.entities.Flower;
import org.springframework.data.jpa.domain.Specification;


public class FlowerSpecifications {
    public static Specification<Flower> hasName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Flower> hasFlower(String flower) {
        return ((root, query, criteriaBuilder) -> {
            if (flower == null || flower.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("flower")), "%" + flower.toLowerCase() + "%");
        });
    }
    public static Specification<Flower> hasPriceFrom(Integer priceFrom) {
        return (((root, query, criteriaBuilder) -> {
            if(priceFrom == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceFrom);
        }));
    }
    public static Specification<Flower> hasPriceTo(Integer priceTo) {
        return (((root, query, criteriaBuilder) -> {
            if(priceTo == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceTo);
        }));
    }
//    public static Specification<Flower> hasType(String type) {
//        return (((root, query, criteriaBuilder) -> {
//            if(type == null || type.isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.like((root.get("quantity")), "%" + type + "%");
//        }));
//    }


}
