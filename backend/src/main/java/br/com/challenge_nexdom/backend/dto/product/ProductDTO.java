package br.com.challenge_nexdom.backend.dto.product;

import br.com.challenge_nexdom.backend.core.models.product.Product;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String code,
        String description,
        String productType,
        BigDecimal supplierPrice,
        Integer stockQuantity
) {
    public static ProductDTO mapper(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getDescription(),
                product.getProductType().name(),
                product.getSupplierPrice(),
                product.getStockQuantity()
        );
    }
}
