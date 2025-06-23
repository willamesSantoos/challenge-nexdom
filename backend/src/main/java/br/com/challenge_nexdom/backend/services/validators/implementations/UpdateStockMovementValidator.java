package br.com.challenge_nexdom.backend.services.validators.implementations;

import br.com.challenge_nexdom.backend.core.models.movement.MovementType;
import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;
import br.com.challenge_nexdom.backend.core.repositories.ProductRepository;
import br.com.challenge_nexdom.backend.core.repositories.StockMovementRepository;
import br.com.challenge_nexdom.backend.exceptions.BusinessRuleException;
import br.com.challenge_nexdom.backend.services.validators.StockMovementValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateStockMovementValidator implements StockMovementValidationStrategy {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    @Override
    public void validate(StockMovement movement) {
        var oldMovement = stockMovementRepository.findById(movement.getId()).get();

        var productId = movement.getProduct().getId();
        var product = productRepository.findById(productId).get();

        int stock = product.getStockQuantity();

        if (oldMovement.getMovementType() == MovementType.IN) {
            stock -= oldMovement.getQuantity();
        } else if (oldMovement.getMovementType() == MovementType.OUT) {
            stock += oldMovement.getQuantity();
        }

        if (movement.getMovementType() == MovementType.IN) {
            stock += movement.getQuantity();
        } else if (movement.getMovementType() == MovementType.OUT) {
            if (movement.getQuantity() > stock) {
                throw new BusinessRuleException("Quantidade solicitada excede o estoque disponível!");
            }

            stock -= movement.getQuantity();
        }

        product.setStockQuantity(stock);
        productRepository.save(product);
    }
}

