package br.com.challenge_nexdom.backend.controllers.specifications;

import br.com.challenge_nexdom.backend.core.models.product.Product;
import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {
    public static Specification<Product> withFilters(
            String description,
            ProductType productType,
            Integer stockQuantity
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (description != null && !description.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            if (productType != null) {
                predicates.add(criteriaBuilder.equal(root.get("productType"), productType));
            }

            if (stockQuantity != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("stockQuantity"), stockQuantity));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
