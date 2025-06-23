package br.com.challenge_nexdom.backend.dto.movement;

import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;
import br.com.challenge_nexdom.backend.dto.product.ProductDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockMovementDTO(
        Long id,
        ProductDTO product,
        String movementType,
        BigDecimal salePrice,
        LocalDateTime movementDate,
        Integer quantity
) {
    public static StockMovementDTO mapper(StockMovement movement) {
        return new StockMovementDTO(
                movement.getId(),
                ProductDTO.mapper(movement.getProduct()),
                movement.getMovementType().name(),
                movement.getSalePrice(),
                movement.getMovementDate(),
                movement.getQuantity()
        );
    }
}
