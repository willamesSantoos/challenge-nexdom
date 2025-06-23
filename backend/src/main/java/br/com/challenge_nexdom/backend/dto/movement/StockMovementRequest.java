package br.com.challenge_nexdom.backend.dto.movement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

public record StockMovementRequest(
        @NotNull(message = "O produto é obrigatório.")
        Long productId,

        @NotBlank(message = "O tipo de movimentação é obrigatório.")
        String movementType,

        @NotNull(message = "O preço de venda é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço de venda deve ser maior que zero.")
        BigDecimal salePrice,

        @NotNull(message = "A data da movimentação é obrigatória.")
        LocalDateTime movementDate,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantity
) {}

