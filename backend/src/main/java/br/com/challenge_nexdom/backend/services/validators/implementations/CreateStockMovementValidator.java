package br.com.challenge_nexdom.backend.services.validators.implementations;

import br.com.challenge_nexdom.backend.core.models.movement.MovementType;
import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;
import br.com.challenge_nexdom.backend.core.repositories.ProductRepository;
import br.com.challenge_nexdom.backend.exceptions.BusinessRuleException;
import br.com.challenge_nexdom.backend.services.validators.StockMovementValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateStockMovementValidator implements StockMovementValidationStrategy {
    private final ProductRepository productRepository;

    @Override
    public void validate(StockMovement movement) {
        var productId = movement.getProduct().getId();
        var product = productRepository.findById(productId).get();

        int currentStock = product.getStockQuantity();
        int quantity = movement.getQuantity();

        if (movement.getMovementType() == MovementType.OUT) {
            if (quantity > currentStock) {
                throw new BusinessRuleException("Quantidade solicitada excede o estoque disponível!");
            }

            product.setStockQuantity(currentStock - quantity);
        }

        if (movement.getMovementType() == MovementType.IN) {
            product.setStockQuantity(currentStock + quantity);
        }

        productRepository.save(product);
    }
}

