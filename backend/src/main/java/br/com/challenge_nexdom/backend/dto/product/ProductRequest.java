package br.com.challenge_nexdom.backend.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "O código do produto é obrigatório.")
        String code,

        @NotBlank(message = "A descrição é obrigatória.")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
        String description,

        @NotBlank(message = "O tipo de produto é obrigatório.")
        String productType,

        @NotNull(message = "O preço do fornecedor é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço do fornecedor deve ser maior que zero.")
        BigDecimal supplierPrice,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
        Integer stockQuantity
) { }